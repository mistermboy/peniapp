package es.uniovi.uo252406.simplefer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.uniovi.uo252406.simplefer.Entities.Question;
import es.uniovi.uo252406.simplefer.Parser;
import es.uniovi.uo252406.simplefer.R;


public class QuizFragment extends android.support.v4.app.Fragment {

    private View view;

    private ArrayList<Question> questions;
    private String person;

    TextView question;
    Button option1;
    Button option2;
    Button option3;

    private int cont = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz, container, false);


        //Recogemos los datos
        Bundle b = getActivity().getIntent().getExtras();
        person = (String) b.getString("person");


        try {
            questions = Parser.getInstance().parse(getJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        prepareComponents();
        startQuiz();

        return view;
    }

    /**
     * Localiza los componentes del QuizFragment
     */
    private void prepareComponents(){

        question = view.findViewById(R.id.textQuestion);
        option1 = view.findViewById(R.id.btnO1);
        option2 = view.findViewById(R.id.btnO2);
        option3 = view.findViewById(R.id.btnO3);

        question.setTextColor(getResources().getColor(R.color.white));
        option1.setTextColor(getResources().getColor(R.color.white));
        option2.setTextColor(getResources().getColor(R.color.white));
        option3.setTextColor(getResources().getColor(R.color.white));

        question.setTextSize(24);
        option1.setTextSize(18);
        option2.setTextSize(18);
        option3.setTextSize(18);

        question.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        option1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        option2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        option3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


    }

    /**
     * Escribe la primera pregunta del quiz y sus respuestas
     */
    private void startQuiz() {

        question.setText(questions.get(cont).getQuestion());
        option1.setText("a) "+questions.get(cont).getOption1());
        option2.setText("b) "+questions.get(cont).getOption2());
        option3.setText("c) "+questions.get(cont++).getOption3());

    }


    public StringBuilder getJson() {

        int rawID = getContext().getResources().getIdentifier(person+"quiz","raw",getContext().getPackageName());


        BufferedReader bR = new BufferedReader(new InputStreamReader(getResources().openRawResource(rawID)));
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
