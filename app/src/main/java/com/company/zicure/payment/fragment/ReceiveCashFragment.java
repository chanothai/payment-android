package com.company.zicure.payment.fragment;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;


import com.company.zicure.payment.R;
import com.company.zicure.payment.adapters.ViewPagerReceiveCashAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiveCashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiveCashFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //View
    private ViewPager pagerReceiveCash = null;
    private TabLayout tabChooseReceiveCash = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int screenHeight = 0;

    public ReceiveCashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiveCashFragment newInstance(String param1, String param2) {
        ReceiveCashFragment fragment = new ReceiveCashFragment();
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
        View root = inflater.inflate(R.layout.fragment_receive_cash, container, false);
        pagerReceiveCash = (ViewPager) root.findViewById(R.id.view_pager_receive_cash);
        tabChooseReceiveCash = (TabLayout) root.findViewById(R.id.tab_choose_receive_cash);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            setupViewPager(pagerReceiveCash);
            tabChooseReceiveCash.setupWithViewPager(pagerReceiveCash);
            resizeView();
        }
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerReceiveCashAdapter adapter = new ViewPagerReceiveCashAdapter(getChildFragmentManager());
        adapter.addFragment(new ReceiveCashWalletFragment(), getString(R.string.txt_wallet));
        adapter.addFragment(new ReceiveCashQrCardFragment(), getString(R.string.txt_qrcard));
        viewPager.setAdapter(adapter);
    }

    private void resizeView(){
        screenHeight = pagerReceiveCash.getRootView().getHeight();
        pagerReceiveCash.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                pagerReceiveCash.getWindowVisibleDisplayFrame(rect);
                if (screenHeight >= rect.bottom){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pagerReceiveCash.getLayoutParams();
                    screenHeight = ((rect.bottom - rect.right));
                    params.height = screenHeight;
                    pagerReceiveCash.setLayoutParams(params);
                }
            }
        });
    }


}
