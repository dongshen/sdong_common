package sdong.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sdong.common.bean.rules.conditional.Conditional;
import sdong.common.bean.rules.conditional.ConditionalNode;
import sdong.common.bean.rules.conditional.ConditionalType;
import sdong.common.exception.SdongException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class RuleUtil {
    /**
     * Get String of Conditional Node
     * 
     * @param node conditional node
     * @return string
     */
    public static String convertConditionalNodeToString(ConditionalNode node){
        String condStr = "";
        if(node == null || node.getType() == ConditionalType.OTHTERS ){
            return condStr;
        }
        return new Gson().toJson(node, ConditionalNode.class);
    }

    public static Conditional convertStringToConditional(String condStr){
        if(condStr == null || condStr.isEmpty()){
            return null;
        }
        
        return new Gson().fromJson(condStr, Conditional.class);
    }

    public static Conditional parseRuleConditioanalJson(String jsonFile) throws SdongException{
        Conditional conditional;
        try (Reader reader = new BufferedReader(new FileReader(jsonFile))) {
            Gson gson = new GsonBuilder().create();
            conditional = gson.fromJson(reader, Conditional.class);                        
        }catch (IOException e){
            throw new SdongException(e.getMessage(), e);
        }

        return conditional;
    }
}
