/*
 * Copyright (C) 2010 The Android Open Source Project
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

package libcore.util;

import dalvik.annotation.optimization.FastNative;

/**
 * Various special-case charset conversions (for performance).
 *
 * @hide internal use only
 */
public final class CharsetUtils {
    /**
     * Returns a new byte array containing the bytes corresponding to the characters in the given
     * string, encoded in US-ASCII. Unrepresentable characters are replaced by (byte) '?'.
     */
    @FastNative
    public static native byte[] toAsciiBytes(String s, int offset, int length);

    /**
     * Returns a new byte array containing the bytes corresponding to the characters in the given
     * string, encoded in ISO-8859-1. Unrepresentable characters are replaced by (byte) '?'.
     */
    @FastNative
    public static native byte[] toIsoLatin1Bytes(String s, int offset, int length);

    /**
     * Returns a new byte array containing the bytes corresponding to the characters in the given
     * string, encoded in UTF-8. All characters are representable in UTF-8.
     */
    @FastNative
    public static native byte[] toUtf8Bytes(String s, int offset, int length);

    /**
     * Returns a new byte array containing the bytes corresponding to the characters in the given
     * string, encoded in UTF-16BE. All characters are representable in UTF-16BE.
     */
    public static byte[] toBigEndianUtf16Bytes(String s, int offset, int length) {
        byte[] result = new byte[length * 2];
        int end = offset + length;
        int resultIndex = 0;
        for (int i = offset; i < end; ++i) {
            char ch = s.charAt(i);
            result[resultIndex++] = (byte) (ch >> 8);
            result[resultIndex++] = (byte) ch;
        }
        return result;
    }

    /**
     * Decodes the given US-ASCII bytes into the given char[]. Equivalent to but faster than:
     *
     * for (int i = 0; i < count; ++i) {
     *     char ch = (char) (data[start++] & 0xff);
     *     value[i] = (ch <= 0x7f) ? ch : REPLACEMENT_CHAR;
     * }
     */
    @FastNative
    public static native void asciiBytesToChars(byte[] bytes, int offset, int length, char[] chars);

    private CharsetUtils() {
    }
}
