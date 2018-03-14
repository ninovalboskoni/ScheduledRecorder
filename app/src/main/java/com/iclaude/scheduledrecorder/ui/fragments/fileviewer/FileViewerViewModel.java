/*
 * Copyright (c) 2018 Claudio "iClaude" Agostini <agostini.claudio1@gmail.com>
 * Licensed under the Apache License, Version 2.0
 */

package com.iclaude.scheduledrecorder.ui.fragments.fileviewer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.iclaude.scheduledrecorder.database.Recording;
import com.iclaude.scheduledrecorder.database.RecordingsRepository;
import com.iclaude.scheduledrecorder.didagger2.App;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.iclaude.scheduledrecorder.database.RecordingsRepositoryInterface.OperationResult;

/**
 * ViewModel for FileViewerFragment.
 */

public class FileViewerViewModel extends ViewModel {
    @Inject
    RecordingsRepository recordingsRepository;
    private LiveData<List<Recording>> recordings;

    public FileViewerViewModel() {
        App.getComponent().inject(this);
    }

    public LiveData<List<Recording>> getRecordings() {
        LiveData<List<Recording>> recordingsLive = recordingsRepository.getAllRecordings();

        return Transformations.map(recordingsLive, recordings1 -> {
            Collections.reverse(recordings1);
            return recordings1;
        });
    }

    public void renameRecording(Recording recording, OperationResult callback) {
        recordingsRepository.updateRecordings(callback, recording);
    }

    public void deleteRecording(Recording recording, OperationResult callback) {
        recordingsRepository.deleteRecordings(callback, recording);
    }
}