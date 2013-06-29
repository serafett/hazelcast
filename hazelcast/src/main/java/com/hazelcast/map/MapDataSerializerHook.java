/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map;

import com.hazelcast.map.operation.*;
import com.hazelcast.map.record.DataRecord;
import com.hazelcast.map.record.ObjectRecord;
import com.hazelcast.monitor.impl.LocalMapStatsImpl;
import com.hazelcast.nio.serialization.*;
import com.hazelcast.query.impl.QueryResultEntryImpl;
import com.hazelcast.util.ConstructorFunction;
import com.hazelcast.util.QueryDataResultStream;

/**
 * @mdogan 8/24/12
 */
public final class MapDataSerializerHook implements DataSerializerHook {

    public static final int F_ID = FactoryIdHelper.getFactoryId(FactoryIdHelper.MAP_DS_FACTORY, -10);

    public static final int PUT = 0;
    public static final int GET = 1;
    public static final int REMOVE = 2;
    public static final int PUT_BACKUP = 3;
    public static final int REMOVE_BACKUP = 4;
    public static final int DATA_RECORD = 5;
    public static final int OBJECT_RECORD = 6;
    public static final int KEY_SET = 7;
    public static final int VALUES = 8;
    public static final int ENTRY_SET = 9;
    public static final int ENTRY_VIEW = 10;
    public static final int MAP_STATS = 11;
    public static final int QUERY_RESULT_ENTRY = 12;
    public static final int QUERY_RESULT_STREAM = 13;

    private static final int LEN = QUERY_RESULT_STREAM + 1;

    public int getFactoryId() {
        return F_ID;
    }

    public DataSerializableFactory createFactory() {
        ConstructorFunction<Integer, IdentifiedDataSerializable> constructors[] = new ConstructorFunction[LEN];

        constructors[PUT] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new PutOperation();
            }
        };
        constructors[GET] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new GetOperation();
            }
        };
        constructors[REMOVE] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new RemoveOperation();
            }
        };
        constructors[DATA_RECORD] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new DataRecord();
            }
        };
        constructors[OBJECT_RECORD] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new ObjectRecord();
            }
        };
        constructors[PUT_BACKUP] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new PutBackupOperation();
            }
        };
        constructors[REMOVE_BACKUP] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new RemoveBackupOperation();
            }
        };
        constructors[KEY_SET] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new MapKeySet();
            }
        };
        constructors[VALUES] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new MapValueCollection();
            }
        };
        constructors[ENTRY_SET] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new MapEntrySet();
            }
        };
        constructors[ENTRY_VIEW] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new SimpleEntryView();
            }
        };
        constructors[MAP_STATS] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new LocalMapStatsImpl();
            }
        };
        constructors[QUERY_RESULT_ENTRY] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new QueryResultEntryImpl();
            }
        };
        constructors[QUERY_RESULT_STREAM] = new ConstructorFunction<Integer, IdentifiedDataSerializable>() {
            public IdentifiedDataSerializable createNew(Integer arg) {
                return new QueryDataResultStream();
            }
        };

        return new ArrayDataSerializableFactory(constructors);
    }
}