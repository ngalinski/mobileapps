// built from MainActivity sample code
// referenced https://guides.codepath.com/android/using-the-recyclerview
// referenced https://www.tutorialsbuzz.com/2019/07/android-dialogfragment-example.html

package edu.neu.madcourse.numad20f_nicholasgalinski.link_collector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.numad20f_nicholasgalinski.R;

public class LinkCollectorFragment extends AppCompatActivity implements DialogueFragment.AddLinkDialogListener {
    private List<LinkItem> linkList;
    private RecyclerView recyclerView;
    private LinkAdapter adapter;
    private static final String STORED_LINKS = "links";
    private static final String STORED_NAMES = "names";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        linkList = new ArrayList<>();

        if (savedInstanceState != null) {
            restorePreviousLinks(savedInstanceState);
        }

        createRecyclerView();

        adapter.setItemListener(new LinkAdapter.LinkItemListener() {
            @Override
            public void onItemClick(int pos) {
                String urlStr = linkList.get(pos).getLinkURL();
                Uri uri = Uri.parse(urlStr);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
                if (launchBrowser.resolveActivity(getPackageManager()) != null) {
                    startActivity(launchBrowser);
                } else {
                    Snackbar.make(recyclerView, "Invalid link - try again.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void restorePreviousLinks(Bundle savedInstanceState) {
        ArrayList<String> names = savedInstanceState.getStringArrayList(STORED_NAMES);
        ArrayList<String> links = savedInstanceState.getStringArrayList(STORED_LINKS);
        for (int i = 0; i < names.size(); i++) {
            try {
                linkList.add(new LinkItem(names.get(i), links.get(i)));
            } catch (NullPointerException e) {
                throw new NullPointerException("Null pointer exception");
            }
        }
    }

    public void linkCollectorOnClick(View v) {
        if (v.getId() == R.id.floating_add_btn) {
            openDialog();
        }
    }

    private void openDialog() {
        DialogueFragment dialog = new DialogueFragment();
        dialog.show(getSupportFragmentManager(), "addDialogFragment");
    }

    private void openPrevDialog(String name, String content) {
        DialogueFragment dialog = DialogueFragment.newInstance(name, content);
        dialog.show(getSupportFragmentManager(), "addDialogFragment");
    }

    public void appendItem(String name, String content) {
        linkList.add(new LinkItem(name, content));
        adapter.notifyItemInserted(linkList.size() - 1);
    }

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.links_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        adapter = new LinkAdapter(linkList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    @Override
    public void onDialogAddClick(final String name, final String content) {
        if (!URLUtil.isValidUrl(content)) {
            Snackbar snackbar = Snackbar.make(recyclerView, "Invalid link", Snackbar.LENGTH_LONG)
                    .setAction(R.string.invalid_link,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openPrevDialog(name, content);
                                }
                            });
            snackbar.show();
            return;
        }
        appendItem(name, content);
        Snackbar.make(recyclerView, "Link added successfully", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle b) {
        super.onSaveInstanceState(b);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> content = new ArrayList<>();
        for (int i = 0; i < linkList.size(); i++) {
            LinkItem curr = linkList.get(i);
            names.add(curr.getName());
            content.add(curr.getLinkURL());
        }
        b.putStringArrayList(STORED_NAMES, names);
        b.putStringArrayList(STORED_LINKS, content);
    }
}