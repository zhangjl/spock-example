/*
 * Copyright 2009 the original author or authors.
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

import spock.lang.*
import spock.util.mop.Use

class DataDrivenSpec extends Specification {
    def "maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [7, 5, 9]
    }

    def "minimum of #a and #b is #c"() {
        expect:
        Math.min(a, b) == c

        where:
        a | b || c
        3 | 7 || 3
        5 | 4 || 4
        9 | 9 || 9
    }

    def 'multi-variable data pipes of #a and #b and #c'(){
        expect:
        a.sum() + b == c

        where:
        [a, [b, _, c]] << [
                [1, 5].permutations(),
                [
                        [2, 3, 8],
                        [1, 4, 7]
                ]
        ].combinations()
    }

    def 'data variable assignment #a and #b and #c'(){
        expect:
        a + b > c
        where:
        a = 3
        b = Double.valueOf(Math.random() * 100).intValue()
        c = Math.max(a, b)
    }

    def 'data variable assignment second style'(){
        expect:
        a + b > 0
        where:
        a | b
        1 | a + 2
        2 | a * 2
        3 | a / 2
        4 | a - 2
    }

    def 'data variable assignment second style model2'(){
        expect:
        d * e + c - a - b > 0

        where:
        a | b
        1 | a + 2
        2 | a * 2
        4 | a - 2

        and:
        c = 1

        and:
        d     | e
        a * 2 | b * 2
        a * 3 | b * 3
        a * 4 | b * 4
    }

    def 'Combining Data Tables, Data Pipes, and Variable Assignments'(){
        expect:
        a + b + c + d > 10

        where:
        a | b
        3 | a + 7
        5 | a + 5
        7 | a + 3

        c << [4, 5, 6]

        d = Math.max(a, c)
    }

    def 'maximum of two numbers max[#a, #b] = #c'(int a, int b, int c){
        expect:
        c == Math.max(a, b)
        where:
        a | b  | c
        1 | 2  | 2
        2 | 10 | 10
        5 | 4  | 5
    }

    def 'maximum of two numbers max[#a, #b] = #c'(){
        expect:
        c == Math.max(a, b)
        where:
        a << [1, 2, 5]
        b << [2, 10, 4]
        c << [2, 10, 5]
    }

    /**
     * 类型强制转换
     * @param i
     */
    def 'type coercion for data variable values'(Integer i){
        expect:
        i instanceof Integer
        i == 10

        where:
        i = '10'
    }

    @Use(CoerceBazToBar)
    def 'custom type coercion'(){
        expect:
        bar.asType() == Bar.FOO

        where:
        bar = Baz.FOO
    }

    def "#person.name is a #sex.toLowerCase() person"() {
        expect:
        person.getSex() == sex

        where:
        person                    || sex
        new Person(name: "Fred")  || "Male"
        new Person(name: "Wilma") || "Female"
    }

    static class Person {
        String name
        String getSex() {
            name == "Fred" ? "Male" : "Female"
        }
    }

    enum Bar {FOO, BAR}
    enum Baz {FOO, BAR}

    class CoerceBazToBar {
        static Bar asType(Baz self, Class<Bar> clazz){
            return Bar.valueOf(self.name())
        }
    }
}
