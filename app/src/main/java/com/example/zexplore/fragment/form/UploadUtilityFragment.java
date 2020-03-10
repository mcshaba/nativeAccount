package com.example.zexplore.fragment.form;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.adapter.AttachmentAdapter;
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.viewmodel.FormViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadUtilityFragment extends Fragment implements CreateAccountFragment.FragmentLifecycle{

    private static String TYPE = "UtilityBill";

    AppCompatImageButton cameraButton, attachUtilityButton;

    ImageView utilityBillImageView;
    private FragmentNavigationListener fragmentNavigationListener;

    private FormViewModel formViewModel;
    private boolean editable;

    private ItemClickListener itemClickListener;

    Form form;

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }

    public interface ItemClickListener{
        void onCameraClicked(String type);
        void onGalleryClicked(String type);
    }

    public UploadUtilityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);
        form = formViewModel.getForm().getValue();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_utitility, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        cameraButton = view.findViewById(R.id.cameraUtilityImageButton);
        attachUtilityButton = view.findViewById(R.id.attachementUtilityImageButton);
        utilityBillImageView = view.findViewById(R.id.emptyImageView);

        if(form == null){

            form = new Form();
            form.setSync(Constant.COMPLETED_FORM);
            form.setStatus(Constant.PENDING_ACCOUNT);
            form.setImageIdentifier(System.currentTimeMillis());
        } else {
            formViewModel.getAttachments(form.getId()).observe(getViewLifecycleOwner(), attachments -> {
                if(attachments != null && attachments.size()> 0){
                    form.setAttachments(attachments);

                }
                formViewModel.setForm(form);
            });
            
            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    removeUtilityBill();
                    itemClickListener.onCameraClicked(TYPE);
                }
            });
            
            attachUtilityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeUtilityBill();
                    itemClickListener.onGalleryClicked(TYPE);
                }
            });
            
            utilityBillObserver();
        }
    }

    private void removeUtilityBill() {
        List<Attachment> attachments = form.getAttachments();
        if (attachments != null && attachments.size() > 0){
            for (int i = 0; i < attachments.size(); i++){
                if (attachments.get(i).getType().equals(TYPE)){
                    formViewModel.deleteAttachment(attachments.get(i).getId());
                    AppUtility.deleteFile(attachments.get(i).getImage());
                    attachments.remove(i);
                }
            }
            form.setAttachments(attachments);
            formViewModel.setForm(form);
            Toast.makeText(requireActivity(), "Utility Bill deleted", Toast.LENGTH_LONG).show();
        }
    }

    private void utilityBillObserver() {
        formViewModel.getForm().observe(getViewLifecycleOwner(), new Observer<Form>() {
            @Override
            public void onChanged(@Nullable Form _form) {
                form = _form;
                if (_form != null){
                    List<Attachment> attachments = form.getAttachments();
                    if (attachments != null && attachments.size() > 0){
                        Attachment attachment = getUtility(attachments);
                        if(attachment != null){
                            Uri uri = AppUtility.retrieveImage(attachment.getImage());
                            utilityBillImageView.setImageURI(uri);
                        }else{
                            utilityBillImageView.setImageResource(R.drawable.no_image);
                        }
                    } else {
                        utilityBillImageView.setImageResource(R.drawable.no_image);
                    }
                }
            }
        });
    }

    private Attachment getUtility(List<Attachment> attachmentList){
        Attachment utilityBill = null;
        for (Attachment attachment: attachmentList){
            if (attachment.getType().equals(TYPE)){
                utilityBill = attachment;
                break;
            }
        }
        return utilityBill;
    }
    @Override
    public void onResume() {
        super.onResume();
        utilityBillObserver();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UploadUtilityFragment.ItemClickListener) {
            itemClickListener = (UploadUtilityFragment.ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AttachmentItemClickListener");
        }

        if (context instanceof FragmentNavigationListener) {
            fragmentNavigationListener = (FragmentNavigationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentNavigationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentNavigationListener = null;
        itemClickListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentNavigationListener = null;
        itemClickListener = null;
    }
}
