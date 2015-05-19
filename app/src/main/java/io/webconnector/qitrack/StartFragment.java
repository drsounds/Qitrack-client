package io.webconnector.qitrack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aleros.tastybean.FindCallback;
import com.aleros.tastybean.TastyException;
import com.aleros.tastybean.TastyObject;
import com.aleros.tastybean.TastyQuery;
import com.aleros.tastybean.TastyResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<TastyObject> mAdapter;
    public ArrayList<TastyObject> accounts = new ArrayList<TastyObject>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<TastyObject>(getActivity(),
                R.layout.item_account, android.R.id.text1, accounts)
        {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    v = inflater.inflate(R.layout.item_account, null);
                }
                TastyObject object = this.getItem(position);
                ((TextView)v.findViewById(R.id.tv_account)).setText(object.get("name").toString());
                NumberFormat numberFormat = NumberFormat.getInstance();
                TextView tvAmount = ((TextView) v.findViewById(R.id.tv_amount));
                tvAmount.setText(numberFormat.format(Float.valueOf(object.get("balance").toString())));
                return v;
            }
        };

        TastyQuery query = new TastyQuery("me/accounts");
        query.getNextBackground(new FindCallback() {
            @Override
            public void done(TastyResult result, TastyException e) {
                if (result != null)
                accounts.addAll(result.objects());
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        ListView lvAccounts = (ListView)v.findViewById(R.id.lvAccounts);
        lvAccounts.setAdapter(mAdapter);
        lvAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TastyObject object = (TastyObject) parent.getItemAtPosition(position);
                try {
                    String account_id = object.get("id").toString();
                    Intent i = new Intent(getActivity(), AccountActivity.class);
                    i.putExtra("id", account_id);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
