/**
 * Copyright (C) 2016 Sergi Castillo Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smassive.daggerworkshopgdg.data.net;

public final class ApiConstants {

    public static final String BASE_URL = "http://gateway.marvel.com";

    public static final String ENDPOINT = BASE_URL + "/v1/public/";

    public static final String QUERY_PARAM_TS = "ts";

    public static final String QUERY_PARAM_API_KEY = "apikey";

    public static final String QUERY_PARAM_HASH = "hash";

    private ApiConstants() {
    }
}
