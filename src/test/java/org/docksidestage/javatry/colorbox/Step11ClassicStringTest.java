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

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.color.BoxColor;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, not using Stream API. <br>
 * Show answer by log() for question of javadoc. <br>
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author robert_devlin
 */
public class Step11ClassicStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * How many lengths does color name of first color-boxes have? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox colorBox = colorBoxList.get(0);
        BoxColor boxColor = colorBox.getColor();
        String colorName = boxColor.getColorName();
        int answer = colorName.length();
        log(answer, colorName); // also show name for visual check
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    String strContent = (String) content;
                    int currentLength = (strContent.length());
                    if (maxStr == null || maxStr.length() < currentLength) {
                        maxStr = ((String) content);
                    }
                }
            }
        }
        log(maxStr != null ? maxStr : "*Not found string content");
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = null;
        String minStr = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    String strContent = (String) content;
                    int currentLength = (strContent.length());
                    if (maxStr == null || maxStr.length() < currentLength) {
                        maxStr = strContent;
                    }
                    if (minStr == null || minStr.length() > currentLength) {
                        minStr = strContent;
                    }
                }
            }
        }
        log(maxStr != null && minStr != null ? maxStr + " " + minStr + " " + (maxStr.length() - minStr.length()) : "not found");
    }

    /**
     * Which value (toString() if non-string) has second-max legnth in color-boxes? (without sort) <br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (ソートなしで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        String maxStr = null;
        String secondMaxStr = null;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content != null) {
                    String strContent = content.toString();
                    int currentLength = (strContent.length());
                    if (maxStr == null || maxStr.length() < currentLength) {
                        secondMaxStr = maxStr;
                        maxStr = strContent;
                    }
                    else if (secondMaxStr == null || secondMaxStr.length() < currentLength) {
                        secondMaxStr = strContent;
                    }
                }
            }
        }
        log((secondMaxStr != null) ? secondMaxStr : "*Second max not initialized");
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int sum = 0;
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) {
                    String strContent = (String) content;
                    int currentLength = (strContent.length());
                    sum += currentLength;
                }
            }
        }
        log("total length is: " + sum);
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String maxStr = null;
        for (ColorBox colorBox : colorBoxList) {
            BoxColor boxColor = colorBox.getColor();
            String strBoxColor = boxColor.toString();
            if (maxStr == null || maxStr.length() < strBoxColor.length()) {
                maxStr = strBoxColor;
            }
        }
        log(maxStr != null ? maxStr : "*Not found string color");
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        String box = "";
        String strBoxColor = "";
        String searchStr = "water";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    if (searchStr == null || ((String) content).toLowerCase().startsWith(searchStr.toLowerCase())) {
                        box = content.toString();

                        BoxColor boxColor = colorBox.getColor();
                        strBoxColor = boxColor.toString();

                        //log(strBoxColor,searchStr,content);
                    }
                }
            }
        }
        log(strBoxColor != "" ? "Found '"+searchStr+"' in the ( "+ box + ") with color ( " + strBoxColor +")"
                : "*No items contained '" + searchStr + "'");
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        String box = "";
        String strBoxColor = "";
        String searchStr = "front";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    if (searchStr == null || ((String) content).toLowerCase().endsWith(searchStr.toLowerCase())) {
                        box = content.toString();

                        BoxColor boxColor = colorBox.getColor();
                        strBoxColor = boxColor.toString();

                        //                        log(strBoxColor, searchStr, content);
                    }
                }
            }
        }
        log(strBoxColor != "" ? "Found '"+searchStr+"' in the ( "+ box + ") with color ( " + strBoxColor +" )"
                : "*No items contained '" + searchStr + "'");
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

        int idx = -1;
        String foundWord = "";
        String searchStr = "front";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    if (((String) content).toLowerCase().endsWith(searchStr.toLowerCase())) {
                        foundWord = content.toString();
                        idx = foundWord.indexOf(searchStr);
                    }
                }
            }
        }
        log(idx != -1 ?
                "Found '" + searchStr + "' at character " + (idx + 1) + " in '" + foundWord + "'":
                "*Search word '" + searchStr + "' not found");
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String foundWord = "";
        String searchStr = "ど";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    if (((String) content).toLowerCase().contains(searchStr.toLowerCase())) {
                        foundWord = content.toString();
                        int firstIdx = foundWord.indexOf(searchStr);
                        int lastIdx = foundWord.lastIndexOf(searchStr);

                        if (firstIdx != lastIdx) log(lastIdx+1);

                    }
                }
            }
        }
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
        String foundWord = "";
        String searchStr = "front";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) { //if the content is of type String
                    if (((String) content).toLowerCase().endsWith(searchStr.toLowerCase())) {
                        foundWord = content.toString();
                    }
                }
            }
        }
        log(foundWord != null ?
                "Found '" + searchStr + "' in '" + foundWord + "'| first character is: '" + foundWord.substring(0,1) + "'":
                "*Search word '" + searchStr + "' not found");
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String foundWord = "";
        String searchStr = "Water";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) {
                    log(content);
                    if (((String) content).toLowerCase().startsWith(searchStr.toLowerCase())) {
                        foundWord = content.toString();
                    }
                }
            }
        }
        log(foundWord != null ?
                "Found '" + searchStr + "' in '" + foundWord + "'| first character is: " + foundWord.substring(foundWord.length()-1):
                "*Search word '" + searchStr + "' not found");
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
        String foundWord = "";
        String searchStr = "o";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof String) {
                    log(content);
                    if (((String) content).toLowerCase().contains(searchStr.toLowerCase())) {
                        foundWord = content.toString();
                    }
                }
            }
        }
        if (foundWord != null) {
            log( "Found '{}' in '{}' | length: {}, length remove '{}': {}",
                    searchStr,foundWord,foundWord.length(),searchStr,foundWord.replace(searchStr,"").length());
        }
        else {
            log("Search string '{}' not found", searchStr);
        }
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String foundWord = "";
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content instanceof File)
                    foundWord = content.toString();
            }
        }
        log(foundWord != "" ?
                foundWord.replace("/","\\"):
                "no file path found"
        );
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        int sum = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content != null)
                    if (content instanceof YourPrivateRoom.DevilBox)
                    {
                        YourPrivateRoom.DevilBox devilBox = (YourPrivateRoom.DevilBox) content;
                        devilBox.wakeUp();
                        devilBox.allowMe();
                        devilBox.open();
                        try {
                            String text = devilBox.getText();
                            if (text != null) {
                                sum += text.length();
                            }
                        }
                        catch(YourPrivateRoom.DevilBoxTextNotFoundException e) {
                            log(e);
                        }
                    }
            }
        }
        log( sum != 0 ? "sum = {}":"no text or no devil boxes found", sum);
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
        boolean checkNested = false;
        String text = "";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content != null) {
                    if (content instanceof LinkedHashMap) {
                        log(map2String((LinkedHashMap) content, checkNested));
                    }
                }
            }
        }
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        boolean checkNested = true;
        String text = "";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content != null) {
                    if (content instanceof LinkedHashMap) {
                        log(map2String((LinkedHashMap) content, checkNested));
                    }
                }
            }
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_flat() {
        String text = "";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            // get upper space
            BoxSpace upperSpace = spaceList.get(0);

            Object content = upperSpace.getContent();
            if (content != null)
                if (content instanceof YourPrivateRoom.SecretBox && colorBox.getColor().getColorName().equals("white")) {
                    YourPrivateRoom.SecretBox secretBox = (YourPrivateRoom.SecretBox) content;
                    try {
                        text = secretBox.getText();
                    }
                    catch (IllegalArgumentException e) {
                        log(e);
                    }
                }
        }
        log( text != "" ? text:"no text found in secret boxes");
    }

    /**
     * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
     * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
     */
    public void test_parseMap_nested() {
        String text = "";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> textMaps = new ArrayList<>();
        for (ColorBox colorBox : colorBoxList) {
            List<BoxSpace> spaceList = colorBox.getSpaceList();
            // remove first item
            spaceList.remove(0);

            for (BoxSpace space : spaceList) {
                Object content = space.getContent();
                if (content != null)
                    if (content instanceof YourPrivateRoom.SecretBox && colorBox.getColor().getColorName() == "white")
                    {
                        YourPrivateRoom.SecretBox secretBox = (YourPrivateRoom.SecretBox) content;
                        try {
                            textMaps.add(secretBox.getText());
                        }
                        catch (IllegalArgumentException e) {
                            log(e);
                        }
                    }
            }
        }
        log( textMaps.size() != 0 ? "\n" + String.join("\n",textMaps) :"no text found in secret boxes");
    }
}

