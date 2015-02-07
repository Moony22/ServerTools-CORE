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
package info.servertools.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import info.servertools.core.util.ClassUtils;
import org.junit.Test;

public class ClassUtilsTest {

    @Test
    public void test() {
        assertTrue(ClassUtils.hasEmptyContructor(TestClass1.class));
        assertTrue(ClassUtils.hasEmptyContructor(TestClass2.class));
        assertTrue(ClassUtils.hasEmptyContructor(TestClass3.class));
        assertFalse(ClassUtils.hasEmptyContructor(TestClass4.class));
    }

    public static class TestClass1 {

    }

    public static class TestClass2 {

        public TestClass2() {

        }
    }

    @SuppressWarnings("UnusedParameters")
    public static class TestClass3 {

        public TestClass3() {

        }

        public TestClass3(String ignored) {

        }
    }

    public static class TestClass4 {

        public TestClass4(String ignored) {

        }
    }
}
