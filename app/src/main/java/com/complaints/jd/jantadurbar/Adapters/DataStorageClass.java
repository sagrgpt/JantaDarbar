/*
 * Copyright (C) 2016 Jd, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.complaints.jd.jantadurbar.Adapters;

/**
 * Created by sagar on 9/4/17.
 */

public class DataStorageClass {

    public String title;
    public String id;
    public String description;
    public String landmark;
    public String wardNo;
    public String city;

    public DataStorageClass(String title, String description, String landmark, String wardNo, String city) {
        this.title = title;
        this.description = description;
        this.landmark = landmark;
        this.wardNo = wardNo;
        this.city = city;
    }

    public DataStorageClass() {
    }
}
