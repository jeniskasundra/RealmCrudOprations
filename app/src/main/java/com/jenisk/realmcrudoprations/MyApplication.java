package com.jenisk.realmcrudoprations;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Jenis Kasundra on 03/05/2018.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .schemaVersion(1)
                .migration(new DataMigration())
                .build();

        Realm.setDefaultConfiguration(config);

    }


    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();

            if (oldVersion == 0) {
                schema.create("Data")
                        .addField("id", int.class)
                        .addField("name", String.class)
                        .addField("address", String.class)
                        .addField("latitude", String.class)
                        .addField("longitude", String.class);
                oldVersion++;
            }

        }
    }

}
