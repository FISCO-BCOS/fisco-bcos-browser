/*
 * Copyright 2014-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.bcos.browser.util;

import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;

/**
 * get Address from String
 */
public class AddressUtils {

    public static String checkAndGetAddress(String addressStr) throws BaseException {
        // validate address
        Address convertAddress = convertAddress(addressStr);
        if (!convertAddress.isValid()) {
            throw new BaseException(ConstantCode.ADDRESS_INVALID);
        }
        return convertAddress.getAddress();
    }

    public static Address convertAddress(String addressStr) {
        Address address = new Address();
        if (addressStr.length() > Address.ValidLen) {
            address.setValid(false);
            address.setAddress(addressStr);
        } else {
            address.setValid(true);
            if (addressStr.startsWith("0x")) {
                if (!addressStr.substring(2, addressStr.length()).matches("^[a-f0-9]+$")) {
                    address.setValid(false);
                    address.setAddress(addressStr);
                } else {
                    if (addressStr.length() == Address.ValidLen) {
                        address.setAddress(addressStr);
                    } else {
                        getAddress(address, addressStr, Address.ValidLen);
                    }
                }

            } else {
                address.setValid(false);
                address.setAddress(addressStr);
            }
        }
        return address;
    }

    private static void getAddress(Address address, String addressStr, int length) {
        int len = length - addressStr.length();
        StringBuilder builderAddress = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builderAddress.append("0");
        }
        String newAddessStr;
        if (length == Address.ValidLen) {
            newAddessStr =
                    "0x" + builderAddress.toString() + addressStr.substring(2, addressStr.length());
        } else {
            newAddessStr = "0x" + builderAddress.toString() + addressStr;
        }
        address.setAddress(newAddessStr);
    }
}
