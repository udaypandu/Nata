package nata.com.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import nata.com.nata.R;


/**
 * Created by Shankar.
 */
public class VideoViewActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private String videoId;
    private String mFrom = "";
    public static final String DEVELOPER_KEY = "AIzaSyCorbJ3Z55DIf0w0L5rzW_CGiXbxSjcK04";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoId = getIntent().getStringExtra("videoId");
        mFrom = getIntent().getStringExtra("from");

        YouTubePlayerView ytpv = (YouTubePlayerView) findViewById(R.id.youtubeplayer);
        ytpv.initialize(DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                                        YouTubeInitializationResult arg1) {
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasrestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.loadVideo(videoId);

    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {

        }

        @Override
        public void onLoaded(String arg0) {

        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            onBackPressed();
        }

        @Override
        public void onVideoStarted() {

        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}