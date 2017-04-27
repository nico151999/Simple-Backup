# Setzen der Grundvariablen fürs Backup
large_files="false" # über Settings Activity kann Nutzer aktivieren, ob er auch data und obb von Interner und Externer SD backupen möchte, true heißt er möchte
app_title="Google Maps" # über Java: pm.getApplicationLabel und wenn return null ist, dann getPackageName
package_name="com.google.android.apps.maps" # über Java: getPackageName
installed_app="/system/app/Maps/Maps.apk" # über Java: getPackageResourcePath
simple_backup_dir="/storage/emulated/0/SimpleBackup" # über Settings Activity von Nutzer festlegbar, bei externer SD-Karte Schreibzugriff über DocumentsUI bzw. Storage Access Framework requesten
app_backup_dir="$simple_backup_dir/$app_title"

am force-stop "$package_name"

if [ ! -d "$simple_backup_dir" ]; then
  mkdir "$simple_backup_dir"
fi

if [ ! -d "$app_backup_dir" ]; then
  mkdir "$app_backup_dir"
else
  rm -rf "$app_backup_dir"
  mkdir "$app_backup_dir"
fi

if [ -a "$simple_backup_dir/$app_title.tar" ]; then
  rm -f "$simple_backup_dir/$app_title.tar"
fi

if [ -a "$simple_backup_dir/$app_title.tar.md5" ]; then
  rm -f "$simple_backup_dir/$app_title.tar.md5"
fi

mkdir "$app_backup_dir/properties"

mkdir "$app_backup_dir/$package_name"

ls -1RpA "/data/data/$package_name" > "$app_backup_dir/includedfiles"

while IFS='' read -r line || [[ -n "$line" ]]; do
  if [ "${line/"data/data/$package_name"}" = "$line" ]; then
    if [ "${line/"/"}" = "$line" ]; then
      if [ -n "$line" ]; then
        if [ -L "$cur_dir/$line" ]; then
          readlink "$cur_dir/$line" > "$app_backup_dir/$package_name${cur_dir/"data/data/$package_name"}/$line"
          stat -c %a "$cur_dir/$line" | xargs > "$app_backup_dir/properties/$cur_dir/link_perm_$line"
          stat -c %G "$cur_dir/$line" | xargs > "$app_backup_dir/properties/$cur_dir/link_group_$line"
          stat -c %U "$cur_dir/$line" | xargs > "$app_backup_dir/properties/$cur_dir/link_user_$line"
          rm -rf "$cur_dir/$line"
        fi
      fi
    else
      if [ -n "${line/"/"}" ]; then
        if [ -L "$cur_dir/${line/"/"}" ]; then
          readlink "$cur_dir/${line/"/"}" > "$app_backup_dir/$package_name${cur_dir/"data/data/$package_name"}/${line/"/"}"
          stat -c %a "$cur_dir/${line/"/"}" | xargs > "$app_backup_dir/properties/$cur_dir/link_perm_${line/"/"}"
          stat -c %G "$cur_dir/${line/"/"}" | xargs > "$app_backup_dir/properties/$cur_dir/link_group_${line/"/"}"
          stat -c %U "$cur_dir/${line/"/"}" | xargs > "$app_backup_dir/properties/$cur_dir/link_user_${line/"/"}"
          rm -rf "$cur_dir/${line/"/"}"
        fi
      fi
    fi
  else
    if [ "$(stat -c %A "${line/":"}" | cut -c1)" = "d" ]; then
      cur_dir="${line/":"}"
      mkdir -p "$app_backup_dir/properties/$cur_dir"
      mkdir -p "$app_backup_dir/$package_name${cur_dir/"data/data/$package_name/"}"
    fi
  fi
done < "$app_backup_dir/includedfiles"

cp -rf "/data/data/$package_name" "$app_backup_dir"

ls -1RpA "/data/data/$package_name" > "$app_backup_dir/temp_includedfiles"

while IFS='' read -r line || [[ -n "$line" ]]; do
  if [ "${line/"data/data/$package_name"}" = "$line" ]; then
    if [ "${line/"/"}" = "$line" ]; then
      if [ -n "$line" ]; then
        if [ -a "$cur_dir/link_perm_$line" ]; then
          if [ -a "$cur_dir/link_group_$line" ]; then
            if [ -a "$cur_dir/link_user_$line" ]; then
              ln -s "$(cat "$app_backup_dir/$package_name${cur_dir/"$app_backup_dir/properties/data/data/$package_name"}/$line")" "${cur_dir/"$app_backup_dir/properties"}/$line"
              chown -h "$(cat "$cur_dir/link_user_$line"):$(cat "$cur_dir/link_group_$line")" "${cur_dir/"$app_backup_dir/properties"}/$line"
            fi
          fi
        fi
      fi
    else
      if [ -n "${line/"/"}" ]; then
        if [ -a "$cur_dir/link_perm_${line/"/"}" ]; then
          if [ -a "$cur_dir/link_group_${line/"/"}" ]; then
            if [ -a "$cur_dir/link_user_${line/"/"}" ]; then
              ln -s "$(cat "$app_backup_dir/$package_name${cur_dir/"$app_backup_dir/properties/data/data/$package_name"}/${line/"/"}")" "${cur_dir/"$app_backup_dir/properties"}/${line/"/"}"
              chown -h "$(cat "$cur_dir/link_user_${line/"/"}"):$(cat "$cur_dir/link_group_${line/"/"}")" "${cur_dir/"$app_backup_dir/properties"}/${line/"/"}"
            fi
          fi
        fi
      fi
    fi
  else
    if [ "$(stat -c %A "$app_backup_dir/properties${line/":"}" | cut -c1)" = "d" ]; then
      cur_dir="$app_backup_dir/properties${line/":"}"
    fi
  fi
done < "$app_backup_dir/includedfiles"

# TODO: data und obb auf externer SD
if [ "$large_files" = "true" ]; then
  if [ -d "/sdcard/Android/data/$package_name" ]; then
    cp -rf "/sdcard/Android/data/$package_name" "$app_backup_dir/Android/data"
  fi
  if [ -d "/sdcard/Android/obb/$package_name" ]; then
    cp -rf "/sdcard/Android/obb/$package_name" "$app_backup_dir/Android/obb"
  fi
fi

while IFS='' read -r line || [[ -n "$line" ]]; do
  if [ "${line/"data/data/$package_name"}" = "$line" ]; then
    if [ "${line/"/"}" = "$line" ]; then
      if [ -n "$line" ]; then
        stat -c %a "$cur_dir/$line" | xargs > "$app_backup_dir/properties/$cur_dir/file_perm_$line"
        stat -c %G "$cur_dir/$line" | xargs > "$app_backup_dir/properties/$cur_dir/file_group_$line"
        stat -c %U "$cur_dir/$line" | xargs > "$app_backup_dir/properties/$cur_dir/file_user_$line"
      fi
    else
      if [ -n "${line/"/"}" ]; then
        stat -c %a "$cur_dir/${line/"/"}" | xargs > "$app_backup_dir/properties/$cur_dir/dir_perm_${line/"/"}"
        stat -c %G "$cur_dir/${line/"/"}" | xargs > "$app_backup_dir/properties/$cur_dir/dir_group_${line/"/"}"
        stat -c %U "$cur_dir/${line/"/"}" | xargs > "$app_backup_dir/properties/$cur_dir/dir_user_${line/"/"}"
      fi
    fi
  else
    if [ "$(stat -c %A "${line/":"}" | cut -c1)" = "d" ]; then
      cur_dir="${line/":"}"
      if [ ! -d "$app_backup_dir/properties/$cur_dir" ]; then
        mkdir -p "$app_backup_dir/properties/$cur_dir"
      fi
    fi
  fi
done < "$app_backup_dir/temp_includedfiles"

stat -c %a "/data/data/$package_name" | xargs > "$app_backup_dir/properties/data/data/dir_perm_$package_name"
stat -c %G "/data/data/$package_name" | xargs > "$app_backup_dir/properties/data/data/dir_group_$package_name"
stat -c %U "/data/data/$package_name" | xargs > "$app_backup_dir/properties/data/data/dir_user_$package_name"

rm -f "$app_backup_dir/temp_includedfiles"

cp -rf "$installed_app" "$app_backup_dir/apk"

echo "$(date)" > "$app_backup_dir/backuptime"

echo "$app_title" > "$app_backup_dir/title"

echo "$package_name" > "$app_backup_dir/package"

tar cfC "$simple_backup_dir/$app_title.tar" "$simple_backup_dir" "$app_title"

md5sum "$simple_backup_dir/$app_title.tar" > "$simple_backup_dir/$app_title.tar.md5"

rm -rf "$app_backup_dir"

# Backupdatumsoutput
# tar xfO "$simple_backup_dir/$app_title.tar" "$app_title/backuptime"

# Backupappnamensoutput
# tar xfO "$simple_backup_dir/$app_title.tar" "$app_title/title"

# Backuppackagenamensoutput
# tar xfO "$simple_backup_dir/$app_title.tar" "$app_title/package"
