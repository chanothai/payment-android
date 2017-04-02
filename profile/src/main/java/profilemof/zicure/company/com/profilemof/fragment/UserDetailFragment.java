package profilemof.zicure.company.com.profilemof.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zicure.company.com.model.util.ModelCart;

import java.util.ArrayList;

import profilemof.zicure.company.com.profilemof.R;
import profilemof.zicure.company.com.profilemof.adapter.UserDetailAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> listTopic = null;
    private ArrayList<String> listContent = null;
    private UserDetailAdapter adapter = null;

    private RecyclerView listItemUserDetail = null;
    public UserDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailFragment newInstance(String param1, String param2) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_detail, container, false);
        listItemUserDetail = (RecyclerView) root.findViewById(R.id.list_item_user_detail);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listItemUserDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState == null){
            setUserDetailView();
        }
    }

    private void initialUserDetail(){
        listTopic = new ArrayList<>();
        listContent = new ArrayList<>();

        listTopic.add(0, getString(R.string.id_card_th));
        listTopic.add(1, getString(R.string.firstname_th));
        listTopic.add(2, getString(R.string.lastname_th));
        listTopic.add(3, getString(R.string.birthday_th));
        listTopic.add(4, getString(R.string.number_phone_th));
    }

    private void setUserDetailView(){
        initialUserDetail();
        adapter = new UserDetailAdapter(listTopic) {
            @Override
            public void onBindViewHolder(UserDetailHolder holder, int position) {
                holder.topic.setText(getTopic(position));
                if (getTopic(position).equalsIgnoreCase(getString(R.string.id_card_th))){

                }
                else if (getTopic(position).equalsIgnoreCase(getString(R.string.firstname_th))){
                    holder.content.setText(ModelCart.getInstance().getUserInfo().getResult().getCustomer().getFirst_name());
                }
                else if (getTopic(position).equalsIgnoreCase(getString(R.string.lastname_th))){
                    holder.content.setText(ModelCart.getInstance().getUserInfo().getResult().getCustomer().getLast_name());
                }
                else if (getTopic(position).equalsIgnoreCase(getString(R.string.birthday_th))){

                }
                else if (getTopic(position).equalsIgnoreCase(getString(R.string.number_phone_th))){

                }
            }
        };

        listItemUserDetail.setAdapter(adapter);
        listItemUserDetail.setItemAnimator(new DefaultItemAnimator());
    }
}
