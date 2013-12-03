package com.pahimar.chitchat.banned;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.pahimar.chitchat.helper.BannedWordHelper;

public class BannedWord implements JsonDeserializer<BannedWord>, JsonSerializer<BannedWord> {

    public static Gson gsonSerializer = (new GsonBuilder()).registerTypeAdapter(BannedWord.class, new BannedWord()).create();

    private String bannedText;
    private boolean mustStartWith;
    private boolean mustEndWith;
    private Pattern bannedPattern;

    private BannedWord() {

    }

    public BannedWord(String bannedWord) {

        this(bannedWord, true, true);
    }

    public BannedWord(String bannedText, boolean mustStartWith, boolean mustEndWith) {

        this.bannedText = bannedText;
        this.mustStartWith = mustStartWith;
        this.mustEndWith = mustEndWith;
        this.bannedPattern = BannedWordHelper.generatePatternFromBannedWord(this);
    }

    public String getBannedText() {

        return bannedText;
    }

    public void setBannedText(String bannedText) {

        this.bannedText = bannedText;
        this.bannedPattern = BannedWordHelper.generatePatternFromBannedWord(this);
    }

    public boolean mustStartWithBannedText() {

        return mustStartWith;
    }

    public void setMustStartWithBannedText(boolean startsWith) {

        this.mustStartWith = startsWith;
        this.bannedPattern = BannedWordHelper.generatePatternFromBannedWord(this);
    }

    public boolean mustEndWithBannedText() {

        return mustEndWith;
    }

    public void setMustEndWithBannedText(boolean mustEndWith) {

        this.mustEndWith = mustEndWith;
        this.bannedPattern = BannedWordHelper.generatePatternFromBannedWord(this);
    }

    public Pattern getPattern() {

        return bannedPattern;
    }

    public static BannedWord createFromJson(String jsonBannedWord) {

        try {
            return gsonSerializer.fromJson(jsonBannedWord, BannedWord.class);
        }
        catch (JsonSyntaxException exception) {
            // TODO Log something regarding the failed parse
        }

        return null;
    }

    public String toJson() {

        return gsonSerializer.toJson(this);
    }

    @Override
    public JsonElement serialize(BannedWord bannedWord, Type type, JsonSerializationContext context) {

        JsonObject jsonBannedWord = new JsonObject();

        if (bannedWord != null) {
            jsonBannedWord.addProperty("bannedText", bannedWord.bannedText);
            jsonBannedWord.addProperty("mustStartWith", bannedWord.mustStartWith);
            jsonBannedWord.addProperty("mustEndWith", bannedWord.mustEndWith);
        }

        return jsonBannedWord;
    }

    @Override
    public BannedWord deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        if (jsonElement.isJsonObject()) {
            
            JsonObject jsonObject = (JsonObject) jsonElement;
            
            String bannedText;
            boolean mustStartWith = true;
            boolean mustEndWith = true;
            
            if (jsonObject.get("bannedText") != null) {

                bannedText = jsonObject.get("bannedText").getAsString();
                
                if (bannedText.length() > 0) {
                    
                    if (jsonObject.get("mustStartWith") != null) {
                        mustStartWith = jsonObject.get("mustStartWith").getAsBoolean();
                    }
                    
                    if (jsonObject.get("mustEndWith") != null) {
                        mustStartWith = jsonObject.get("mustEndWith").getAsBoolean();
                    }
                    
                    return new BannedWord(bannedText, mustStartWith, mustEndWith);
                }
                else {
                    throw new JsonParseException("Banned word cannot be 0 characters in length");
                }
            }
            else {
                throw new JsonParseException(String.format("No banned word found for the JsonElement", jsonElement));
            }
        }
        
        return null;
    }

}
