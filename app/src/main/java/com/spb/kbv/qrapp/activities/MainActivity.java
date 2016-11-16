package com.spb.kbv.qrapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spb.kbv.qrapp.services.Api;
import com.spb.kbv.qrapp.model.Note;
import com.spb.kbv.qrapp.views.NotesAdapter;
import com.spb.kbv.qrapp.R;
import com.spb.kbv.qrapp.services.WebService;

import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler, AdapterView.OnItemClickListener {

    public static final String WEB_URL = "WEB_URL";
    private ZBarScannerView mScannerView;
    private Api mApi;
    private NotesAdapter mNotesAdapter;
    private List<Note> mNotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNotesList = Note.listAll(Note.class);
        mApi = WebService.createWebService();

        initializeUiElements();
    }

    private void initializeUiElements() {
        setContentView(R.layout.activity_main);
        mScannerView = (ZBarScannerView) findViewById(R.id.scanner);
        mNotesAdapter = new NotesAdapter(mNotesList, this);
        ListView mListView = (ListView) findViewById(R.id.list_view);
        assert mListView != null;
        mListView.setAdapter(mNotesAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Note newNote = new Note(result.getContents());
        if (!mNotesList.contains(newNote)) {
            newNote.save();
            mNotesList.add(newNote);
            if (newNote.isUrl()) {
                getNoteStatusCode(newNote, mNotesList.size() - 1);
            }
        }
        // Resumes scanning
        mScannerView.resumeCameraPreview(this);
    }

    //Gets status code of url
    private void getNoteStatusCode(final Note note, final int noteListPosition) {
        mApi.getStatus(note.getNoteName()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateNoteStatusCodeById(note.getId(), noteListPosition, response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

    // Saves note to database and refreshes listview adapter
    private void updateNoteStatusCodeById(long id, int  noteListPosition, int statusCode) {
        Note noteToUpdate = Note.findById(Note.class, id);
        noteToUpdate.setStatusCode(String.valueOf(statusCode));
        noteToUpdate.save();
        mNotesList.get(noteListPosition).setStatusCode(String.valueOf(statusCode));
        mNotesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Note noteForShow = mNotesAdapter.getItem(position);
        assert noteForShow != null;
        if (noteForShow.isUrl()) {
            Intent intent = new Intent(MainActivity.this, WebActivity.class);
            intent.putExtra(WEB_URL, noteForShow.getNoteName());
            startActivity(intent);
        }
    }
}
