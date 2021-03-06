/*
 * This file is a part of ServerTools <http://servertools.info>
 *
 * Copyright (c) 2014 ServerTools
 * Copyright (c) 2014 contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.servertools.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Variety of helper methods for dealing with serializing/deserializing via {@link com.google.gson.Gson Gson}
 */
public final class GsonUtils {

    private static final Gson gson = new Gson();
    private static final Gson gson_pp = new GsonBuilder().setPrettyPrinting().create();

    private GsonUtils() {
    }

    /**
     * Serialize an Object into it's Json representation
     *
     * @param object         the object to serialize
     * @param prettyPrinting if 'pretty priniting' should be used in the generated JSON
     *
     * @return the JSON
     */
    public static String toJson(Object object, boolean prettyPrinting) {

        if (prettyPrinting) {
            return gson_pp.toJson(object);
        } else {
            return gson.toJson(object);
        }
    }
}
