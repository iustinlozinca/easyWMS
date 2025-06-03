package com.example.orders.help;

import android.net.Uri;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import java.util.function.Consumer;
public class FilePickerHelper {

    private final String[] mimeTypes;
    private final ActivityResultLauncher<String[]> launcher;

    public FilePickerHelper(@NonNull ActivityResultCaller caller,
                            @NonNull Consumer<Uri> callback,
                            @NonNull String... mimeTypes) {
        this.mimeTypes = mimeTypes;

        launcher = caller.registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) callback.accept(uri);
                });
    }

    public void launch() {
        launcher.launch(mimeTypes);
    }
}
