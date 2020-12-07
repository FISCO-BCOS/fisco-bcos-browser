/*
 * Copyright 2014-2020 the original author or authors.
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

package org.bcos.browser.util;

/**
 * This Address is different from org.fisco.bcos.web3j.abi.datatypes.Address
 * used to check validity of address in AddressUtils
 */
public class Address {

    private boolean valid;
    private String address;

    public static final int ValidLen = 42;

    public Address() {}

    public Address(boolean valid, String address) {
        this.valid = valid;
        this.address = address;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address [valid=" + valid + ", \naddress=" + address + "]";
    }
}

