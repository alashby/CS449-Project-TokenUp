package com.github.cs449project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Al on 2/5/2018.
 */

public class Token {

    private String _id;
    private String _tokenName;
    private String _imgFile;
    private String _type;
    private String _subType;
    private String _set;
    private String _artist;
    private String _colors;
    private String _tags;

    public Token() {
    }

    public Token(String id) {
        this._id = id;
    }

    public Token
            (String id, String tokenName, String imgFile, String type, String subType, String set,
             String artist, String colors, String tags) {
        this._id = id;
        this._tokenName = tokenName;
        this._imgFile = imgFile;
        this._type = type;
        this._subType = subType;
        this._set = set;
        this._artist = artist;
        this._colors = colors;
        this._tags = tags;
    }

    public void setId(String id) {
        this._id = id;
    }
    public void setTokenName(String tokenName) {
        this._tokenName = tokenName;
    }
    public void setImgFile(String imgFile) {
        this._imgFile = imgFile;
    }
    public void setType(String type) {
        this._type = type;
    }
    public void setSubType(String subType) {
        this._subType = subType;
    }
    public void setSet(String set) {
        this._set = set;
    }
    public void setArtist(String artist) {
        this._artist = artist;
    }
    public void setColors(String colors) {
        this._colors = colors;
    }
    public void setTags(String tags) {
        this._tags = tags;
    }

    public String getId() {
        return this._id;
    }
    public String getTokenName() {
        return this._tokenName;
    }
    public String getImgFile() {
        return this._imgFile;
    }
    public String getType() {
        return this._type;
    }
    public String getSubType() {
        return this._subType;
    }
    public String getSet() {
        return this._set;
    }
    public String getArtist() {
        return this._artist;
    }
    public String getColors() {
        return this._colors;
    }
    public String getTags() {
        return this._tags;
    }
}
