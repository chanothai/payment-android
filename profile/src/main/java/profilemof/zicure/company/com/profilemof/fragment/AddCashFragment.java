package profilemof.zicure.company.com.profilemof.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.zicure.company.com.model.util.ModelCart;

import gallery.zicure.company.com.gallery.util.ResizeScreen;
import profilemof.zicure.company.com.profilemof.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCashFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MultiFormatWriter multiFormatWriter = null;

    //View
    private ImageView imgQrCode = null;
    private RelativeLayout layoutAddCash = null;

    private ViewTreeObserver viewTreeObserver = null;
    private int width = 0;
    private int height = 0;

    public AddCashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCashFragment newInstance(String param1, String param2) {
        AddCashFragment fragment = new AddCashFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_cash, container, false);
        imgQrCode = (ImageView) root.findViewById(R.id.qrCode);
        layoutAddCash = (RelativeLayout) root.findViewById(R.id.layout_add_cash);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            resizeImgQRCode();
        }
    }

    private void resizeImgQRCode(){
        ResizeScreen resizeScreen = new ResizeScreen(getActivity());
        int width = resizeScreen.widthScreen(1);
        generateQRCode(width, width);
    }

    private void generateQRCode(int width, int height){
        if (multiFormatWriter == null){
            multiFormatWriter = new MultiFormatWriter();
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode(ModelCart.getInstance().getToken().getResult().getAccountNo(), BarcodeFormat.QR_CODE, width, height);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imgQrCode.setImageBitmap(bitmap);
            }catch (WriterException e){
                e.printStackTrace();
            }
        }
    }
}
