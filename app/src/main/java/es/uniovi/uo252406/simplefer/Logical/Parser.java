package es.uniovi.uo252406.simplefer.Logical;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Logical.Entities.PersonalData;
import es.uniovi.uo252406.simplefer.Logical.Entities.Question;


public class Parser {

    private ArrayList<Question> questions;

    private JSONObject jsonObject;
    private JSONObject person;

    private JSONObject data;

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


    public ArrayList<Question> getQuiz(View view,String pName) throws JSONException {

        String question;
        String option1;
        String option2;
        String option3;
        int answer;

        questions = new ArrayList<Question>();
        jsonObject = new JSONObject(getJson(view,pName).toString());

        person = jsonObject.getJSONObject("person");
        quiz = person.getJSONObject("quiz");


        for (int i = 1; i <= quiz.length(); i++) {

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


    public PersonalData getData(View view,String pName) throws JSONException {

        String name;
        String description;

        jsonObject = new JSONObject(getJson(view,pName).toString());

        person = jsonObject.getJSONObject("person");
        data = person.getJSONObject("data");

        name = data.getString("name");
        description = data.getString("description");


        return new PersonalData(name,description);

    }

    public StringBuilder getJson(View view,String person) {

        int rawID = view.getContext().getResources().getIdentifier(person,"raw",view.getContext().getPackageName());


        BufferedReader bR = new BufferedReader(new InputStreamReader(view.getResources().openRawResource(rawID)));
        StringBuilder sB = new StringBuilder();
        String linea = null;

        try {
            while ((linea = bR.readLine()) != null) {
                sB.append(linea).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sB;
    }

}
