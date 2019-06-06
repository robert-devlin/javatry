/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.io.File;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;
//import org.graalvm.compiler.hotspot.nodes.PluginFactory_JumpToExceptionHandlerNode;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author robert_devlin
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                .map(colorBox -> colorBox.getColor()) // consciously split as example
                .map(boxColor -> boxColor.getColorName())
                .map(colorName -> {
                    log(colorName); // for visual check
                    return String.valueOf(colorName.length());
                })
                .orElse("not found"); // basically no way because of not-empty list and not-null returns
        log(answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .reduce((w1,w2) -> w1.length()> w2.length() ? w1 : w2)
                .orElse(null);
        log(answer);
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);

        String minStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .min(Comparator.comparingInt(String::length))
                .orElse(null);
        if(minStr != null && maxStr != null)
            log("length of '" + maxStr + "' - length of '" + minStr + "' = " + (maxStr.length()-minStr.length()));
    }

    // has small #adjustmemts from ClassicStringTest
    //  o sort allowed in Stream
    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (sort allowed in Stream)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (Streamでのソートありで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .sorted(Comparator.comparingInt(String::length).reversed())
                .skip(1)
                .findFirst()
                .orElse(null);
        log("secondMax is " + maxStr);
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Integer sum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .mapToInt(content ->((String) content).length())
                .sum();
        log("sum of string lengths is " + sum);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        OptionalInt max = colorBoxList.stream()
                .map(colorBox -> colorBox.getColor())
                .map(boxColor -> boxColor.getColorName())
                .map(colorName -> colorName.length())
                .mapToInt(Integer::intValue)
                .max();
        if (!max.isPresent()){
            log("could not calculate max");
        }
        else
            log(max.getAsInt());
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        //ダイキ実装式
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> answer = colorBoxList.stream()
                .filter(colorBox -> {
                    boolean front = colorBox.getSpaceList().stream()
                            .map(boxSpace -> boxSpace.getContent())
                            .filter(content -> content instanceof String)
                            .anyMatch(content -> ((String)content).startsWith("Water"));
                    return front;
                }).map(colorBox -> colorBox.getColor().getColorName()).findAny();

        if(answer.isPresent()){
            log(answer.get());
        }
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        //ダイキ実装式
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> answer = colorBoxList.stream()
                                              .filter(colorBox -> {
                                                        boolean front = colorBox.getSpaceList().stream()
                                                                        .map(boxSpace -> boxSpace.getContent())
                                                                        .filter(content -> content instanceof String)
                                                                        .anyMatch(content -> ((String)content).endsWith("front"));
                                                        return front;
        }).map(colorBox -> colorBox.getColor().getColorName()).findFirst();

        if(answer.isPresent()){
            log(answer.get());
        }

    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with first "front" of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列で、最初の "front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> foundIdx = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .filter(strContent -> strContent.endsWith("front"))
                .map(content -> content.indexOf("front"))
                .findFirst();

        if(foundIdx.isPresent())
            log(foundIdx.get());
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> foundIdx = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> (String) content)
                .filter(strContent -> strContent.contains("ど"))
                .map(strContent -> strContent.lastIndexOf("ど"))
                .findFirst();

        if(foundIdx.isPresent())
            log(foundIdx.get());
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> foundWord = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(strContent -> strContent.endsWith("front"))
                .map(strContent -> strContent.substring(0,1))
                .findFirst();

        if(foundWord.isPresent())
            log(foundWord.get());

    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional <String> foundWord = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(strContent -> strContent.startsWith("Water"))
                .map(strContent -> strContent.substring(strContent.length()-1))
                .findFirst();

        if(foundWord.isPresent())
            log(foundWord.get());
        else
            log("no words found starting with 'Water'");
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String removeStr = "o";
        Optional<String> foundWord = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(strContent -> strContent.contains(removeStr))
                .findFirst();

        if(foundWord.isPresent()) {
            String foundWordRemoveChar = foundWord.get().replace(removeStr, "");
            log(String.format("%s: %d | %s: %d", foundWord.get(), foundWord.get().length(), foundWordRemoveChar, foundWordRemoveChar.length()));
        }
        else
            log("no words found starting with 'Water'");
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> foundWord = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof File)
                .map(content -> content.toString())
                .findFirst();

        if(foundWord.isPresent())
        {
            log(foundWord.get());
            log(foundWord.get().replace("/","\\"));
        }
        else
            log("no words found starting with 'Water'");
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public String devilBoxHandler(YourPrivateRoom.DevilBox db){
        db.wakeUp();
        db.allowMe();
        db.open();
        try {
            String text = db.getText();
            if (text == null) {
                throw new YourPrivateRoom.DevilBoxTextNotFoundException("text is null!");
            }
            return text;
        }
        catch(YourPrivateRoom.DevilBoxTextNotFoundException e) {
            log(e);
            return "";
        }
    }

    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int sum = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof YourPrivateRoom.DevilBox)
                .map(db -> devilBoxHandler((YourPrivateRoom.DevilBox) db))
                .mapToInt(dbText -> dbText.length())
                .sum();
        log(sum);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public String map2String(LinkedHashMap hm, boolean isNested) {
        Iterator it = hm.entrySet().iterator();
        StringBuilder s = new StringBuilder("map:{");
        while(it.hasNext()){
            Map.Entry kv = (Map.Entry) it.next();
            Object k = kv.getKey();
            Object v = kv.getValue();
            if(k == null){
                k = "null";
            }
            if(v == null){
                v = "null";
            }
            if(v instanceof LinkedHashMap && isNested){
                v = map2String((LinkedHashMap) v, true);
            }
            String entry = String.format(" %s = %s ;",k,v);
            s.append(entry);
        }
        //clean up the end
        s.deleteCharAt(s.length()-1);
        s.append('}');
        return s.toString();
    }


    public void test_showMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> innerMap = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof LinkedHashMap)
                .map(content -> map2String((LinkedHashMap) content,false))
                .collect(Collectors.toList());

        for (String s : innerMap) {
            log(s);
        }


    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> innerMap = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof LinkedHashMap)
                .map(content -> map2String((LinkedHashMap) content,true))
                .collect(Collectors.toList());

        for (String s : innerMap) {
            log(s);
        }

    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // has small #adjustmemts from ClassicStringTest
    //  o comment out because of too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}
}
