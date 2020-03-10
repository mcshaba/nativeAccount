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
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.viewmodel.FormViewModel;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadPassportFragment extends Fragment implements CreateAccountFragment.FragmentLifecycle {

    AppCompatImageButton cameraButton, attachPassportButton;
    ImageView passportImageView;
    private FragmentNavigationListener fragmentNavigationListener;

    Form form;
    private FormViewModel formViewModel;
    public final static String TYPE = "PassportPhoto";
    private ItemClickListener itemClickListener;

    public UploadPassportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }

    public interface ItemClickListener {
        void onCameraClicked(String type);

        void onGalleryClicked(String type);
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
        return inflater.inflate(R.layout.fragment_upload_passport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        cameraButton = view.findViewById(R.id.cameraImageButton);
        attachPassportButton = view.findViewById(R.id.attachementImageButton);
        passportImageView = view.findViewById(R.id.emptyImageView);

        if (form == null) {
            form.setSync(Constant.COMPLETED_FORM);
            form.setStatus(Constant.PENDING_ACCOUNT);
            form.setImageIdentifier(System.currentTimeMillis());
        } else {
            formViewModel.getAttachments(form.getId()).observe(getViewLifecycleOwner(), attachments -> {
                if (attachments != null && attachments.size() > 0) {
                    form.setAttachments(attachments);
                }
                formViewModel.setForm(form);
            });
        }
        formViewModel.setForm(form);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePassport();
                itemClickListener.onCameraClicked(TYPE);
            }
        });

        attachPassportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePassport();
                itemClickListener.onGalleryClicked(TYPE);
            }
        });

        passportPhotoObserver();
    }

    private void passportPhotoObserver() {
        formViewModel.getForm().observe(requireActivity(), new Observer<Form>() {
            @Override
            public void onChanged(Form form_) {
                form = form_;
                if (form_ != null) {
                    List<Attachment> attachments = form.getAttachments();
                    if (attachments != null && attachments.size() > 0) {
                        Attachment attachment = getPassport(attachments);
                        if (attachment != null) {
                            Uri uri = AppUtility.retrieveImage(attachment.getImage());
                            passportImageView.setImageURI(uri);
                        } else {
                            passportImageView.setImageResource(R.drawable.no_image);
                        }
                    } else {
                        passportImageView.setImageResource(R.drawable.no_image);
                    }
                }
            }
        });
    }

    private Attachment getPassport(List<Attachment> attachmentList) {
        Attachment passportPhoto = null;
        for (Attachment attachment : attachmentList) {
            if (attachment.getType().equals(TYPE)) {
                passportPhoto = attachment;
                break;
            }
        }
        return passportPhoto;
    }

    private void removePassport() {
        List<Attachment> attachments = form.getAttachments();
        if (attachments != null && attachments.size() > 0) {
            for (int i = 0; i < attachments.size(); i++) {
                if (attachments.get(i).getType().equals(TYPE)) {
                    formViewModel.deleteAttachment(attachments.get(i).getId());
                    AppUtility.deleteFile(attachments.get(i).getImage());
                    attachments.remove(i);
                }
            }
            form.setAttachments(attachments);
            formViewModel.setForm(form);
            Toast.makeText(requireActivity(), "Passport photo deleted", Toast.LENGTH_LONG).show();
        }
    }

    private void saveForm() {
        Toast.makeText(requireActivity(), form.getAccountTypeName() + "FORM SAVED ", Toast.LENGTH_LONG).show();
        fragmentNavigationListener.onSaveFormClicked(form);
    }


    @Override
    public void onResume() {
        super.onResume();
        passportPhotoObserver();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            itemClickListener = (ItemClickListener) context;
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
