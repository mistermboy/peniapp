package es.uniovi.uo252406.simplefer.Logical;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Entities.Question;

public class Parser {

    private ArrayList<Question> questions;
    private JSONObject jsonObject;
    private JSONObject quiz;
    private JSONObject qObject;
    private JSONArray oArray;


    private static Parser instance = new Parser();

    private Parser(){
    }

    public static Parser getInstance(){
        if(instance == null){
            instance = new Parser();
        }
        return  instance;
    }


    public ArrayList<Question> parse(StringBuilder json) throws JSONException {

        String question;
        String option1;
        String option2;
        String option3;
        int answer;

        questions = new ArrayList<Question>();
        jsonObject = new JSONObject(json.toString());

        Log.i("DATOS", jsonObject.toString());

        quiz = jsonObject.getJSONObject("quiz");


        for (int i = 1; i < quiz.length(); i++) {

            qObject = quiz.getJSONObject("q" + i);
            question = qObject.getString("question");

            oArray = qObject.getJSONArray("options");
            option1 = (String) oArray.get(0);
            option2 = (String) oArray.get(1);
            option3 = (String) oArray.get(2);

            answer = qObject.getInt("answer");

            questions.add(new Question(question, option1, option2, option3, answer));

        }

        return questions;

    }


}
