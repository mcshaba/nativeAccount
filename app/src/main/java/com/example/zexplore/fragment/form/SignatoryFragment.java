package com.example.zexplore.fragment.form;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.viewmodel.FormViewModel;
import com.kyanogen.signatureview.SignatureView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignatoryFragment extends Fragment implements CreateAccountFragment.FragmentLifecycle{

    SignatureView signaturePad;
    ImageView signaturePhoto;

    Button submit;

    private FormViewModel formViewModel;
    private boolean editable;

    public static String TYPE = "Signatory";

    private FragmentNavigationListener fragmentNavigationListener;

    Form form;
    public SignatoryFragment() {
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
        return inflater.inflate(R.layout.fragment_signatory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        signaturePhoto = view.findViewById(R.id.signature_preview);
        signaturePad = view.findViewById(R.id.signature_view);

        signaturePhoto.setOnClickListener(v -> {
            removeSignature();
            showSignaturePad();
            Toast.makeText(getContext(), "Editor Mode", Toast.LENGTH_LONG).show();
        });


        submit = view.findViewById(R.id.submit_form_button);


        submit.setOnClickListener(view1 -> submitAction());
        attachmentObserver();

        //disableControls();

        signatureObserver();

    }

    private void submitAction() {
        if(captureSignature()){
            //Toast.makeText(requireActivity(), "SENDING " + form.getAccountTypeName().toUpperCase() + " FORM", Toast.LENGTH_LONG).show();

            if(PersonalInformationFragment.completed && AccountInfoFragment.completed && ContactDetailsFragment.completed) {
                fragmentNavigationListener.onSubmitFormClicked(form);
                closefragment();
            } else {
                Toast.makeText(requireActivity(), "Some Fields are empty", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void closefragment() {
        requireActivity().getFragmentManager().popBackStack();
    }


    private void signatureObserver() {
        formViewModel.getForm().observe(getViewLifecycleOwner(), _form -> {
            form = _form;
            if (_form != null){
                List<Attachment> attachments = form.getAttachments();
                if (attachments != null && attachments.size() > 0){
                    Attachment attachment = getSignature(attachments);
                    if(attachment != null){
                        Uri uri = AppUtility.retrieveImage(attachment.getImage());
                        signaturePhoto.setImageURI(uri);
                    }
                }
            }
        });
    }


    private void disableControls(){
        signaturePad.setEnabled(editable);
    }

    private void attachmentObserver(){
        formViewModel.getForm().observe(requireActivity(), new Observer<Form>() {
            @Override
            public void onChanged(@Nullable Form _form) {
                form = _form;
                if (_form != null){
                    List<Attachment> attachments = _form.getAttachments();
                    if (attachments != null && attachments.size() > 0){
                        Attachment attachment = AppUtility.getAttachedImage(attachments, TYPE);
                        if (attachment != null && attachment.getImage() != null){
                            Uri uri = AppUtility.retrieveImage(attachment.getImage());
                            signaturePhoto.setImageURI(uri);
                            //hideSignaturePad();
                            showSignaturePad();
                        } else {
                            showSignaturePad();
                        }
                    } else {
                        showSignaturePad();
                    }
                }
            }
        });
    }

    private Attachment getSignature(List<Attachment> attachmentList){
        Attachment signaturePhoto = null;
        for (Attachment attachment: attachmentList){
            if (attachment.getType().equals(TYPE)){
                signaturePhoto = attachment;
                break;
            }
        }
        return signaturePhoto;
    }

    private void hideSignaturePad(){
        signaturePad.setVisibility(View.GONE);
        //signaturePreview.setVisibility(View.VISIBLE);
    }

    private void showSignaturePad(){
        signaturePad.setVisibility(View.VISIBLE);
        //signaturePreview.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
            }
        }

        MenuItem save = menu.findItem(R.id.save_form);
        if (editable){
            save.setVisible(true);
        } else {
            save.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void removeSignature(){
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
            if (!signaturePad.isBitmapEmpty())signaturePad.clearCanvas();
            Toast.makeText(getActivity(), "Signature deleted, you can sign again", Toast.LENGTH_LONG).show();
        }
    }

    private boolean captureSignature(){
        List<Attachment> attachments = form.getAttachments();
        if (attachments == null) attachments = new ArrayList<>();
        Attachment attachment = AppUtility.getAttachedImage(attachments, TYPE);
        if (attachment == null) attachment = new Attachment(null, TYPE);

        if (!signaturePad.isBitmapEmpty() && attachment.getImage() == null) {
            Bitmap bitmap = signaturePad.getSignatureBitmap();
            if (bitmap != null){
                String path = AppUtility.saveImage(requireActivity(), bitmap, TYPE, form.getImageIdentifier());
                attachment.setImage(path);
            }
            attachments.add(attachment);
            form.setAttachments(attachments);
            signaturePad.clearCanvas();
            return true;
        } else if (attachment.getImage() != null){
            form.setAttachments(attachments);
            signaturePad.clearCanvas();

            return true;
        } else {
            Toast.makeText(getActivity(), "KINDLY SIGN YOUR SIGNATURE", Toast.LENGTH_LONG).show();
            return false;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        attachmentObserver();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentNavigationListener = null;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
