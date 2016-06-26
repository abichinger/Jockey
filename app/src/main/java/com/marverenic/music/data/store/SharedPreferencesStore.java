package com.marverenic.music.data.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.audiofx.Equalizer;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.marverenic.music.R;
import com.marverenic.music.utils.BaseTheme;
import com.marverenic.music.utils.PresetTheme;
import com.marverenic.music.utils.StartPage;

public class SharedPreferencesStore implements PreferencesStore {

    private static final String TAG = "SharedPreferencesStore";

    private Context mContext;
    private SharedPreferences mPrefs;

    public SharedPreferencesStore(Context context) {
        mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private boolean contains(@StringRes int keyRes) {
        return mPrefs.contains(mContext.getString(keyRes));
    }

    private boolean getBoolean(@StringRes int keyRes, boolean defaultValue) {
        return mPrefs.getBoolean(mContext.getString(keyRes), defaultValue);
    }

    private int getInt(@StringRes int keyRes, int defaultValue) {
        return mPrefs.getInt(mContext.getString(keyRes), defaultValue);
    }

    private String getString(@StringRes int keyRes, String defaultValue) {
        return mPrefs.getString(mContext.getString(keyRes), defaultValue);
    }

    private void putBoolean(@StringRes int keyRes, boolean value) {
        mPrefs.edit()
                .putBoolean(mContext.getString(keyRes), value)
                .apply();
    }

    private void putInt(@StringRes int keyRes, int value) {
        mPrefs.edit()
                .putInt(mContext.getString(keyRes), value)
                .apply();
    }

    private void putString(@StringRes int keyRes, String value) {
        mPrefs.edit()
                .putString(mContext.getString(keyRes), value)
                .apply();
    }

    @Override
    public boolean showFirstStart() {
        return getBoolean(R.string.pref_key_show_first_start, true);
    }

    @Override
    public boolean allowLogging() {
        return getBoolean(R.string.pref_key_allow_logging, false);
    }

    @Override
    public boolean useMobileNetwork() {
        return getBoolean(R.string.pref_key_use_mobile_net, true);
    }

    @Override
    public boolean openNowPlayingOnNewQueue() {
        return getBoolean(R.string.pref_key_switch_to_playing, true);
    }

    @Override
    public boolean enableNowPlayingGestures() {
        return getBoolean(R.string.pref_key_enable_gestures, true);
    }

    @Override
    @SuppressWarnings("WrongConstant")
    public int getDefaultPage() {
        return getInt(R.string.pref_key_default_page, StartPage.SONGS);
    }

    @Override
    @SuppressWarnings("WrongConstant")
    public int getPrimaryColor() {
        return getInt(R.string.pref_key_color_primary, PresetTheme.BLUE);
    }

    @Override
    @SuppressWarnings("WrongConstant")
    public int getBaseColor() {
        return getInt(R.string.pref_key_color_base, BaseTheme.LIGHT);
    }

    @Override
    public boolean isShuffled() {
        return getBoolean(R.string.pref_key_shuffle, false);
    }

    @Override
    public int getRepeatMode() {
        return getInt(R.string.pref_key_repeat, 0);
    }

    @Override
    public int getEqualizerPresetId() {
        return getInt(R.string.pref_key_eq_id, -1);
    }

    @Override
    public boolean getEqualizerEnabled() {
        return getBoolean(R.string.pref_key_eq_enabled, false);
    }

    @Override
    public Equalizer.Settings getEqualizerSettings() {
        if (contains(R.string.pref_key_eq_settings)) {
            try {
                return new Equalizer.Settings(getString(R.string.pref_key_eq_settings, null));
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "getEqualizerSettings: failed to parse equalizer settings", e);
                Crashlytics.logException(e);
            }
        }
        return null;
    }

    @Override
    public void setShowFirstStart(boolean showFirstStart) {
        putBoolean(R.string.pref_key_show_first_start, showFirstStart);
    }

    @Override
    public void setAllowLogging(boolean allowLogging) {
        putBoolean(R.string.pref_key_allow_logging, allowLogging);
    }

    @Override
    public void setUseMobileNetwork(boolean useMobileNetwork) {
        putBoolean(R.string.pref_key_use_mobile_net, useMobileNetwork);
    }

    @Override
    public void setOpenNowPlayingOnNewQueue(boolean openNowPlayingOnNewQueue) {
        putBoolean(R.string.pref_key_switch_to_playing, openNowPlayingOnNewQueue);
    }

    @Override
    public void setEnableNowPlayingGestures(boolean enabled) {
        putBoolean(R.string.pref_key_enable_gestures, enabled);
    }

    @Override
    public void setDefaultPage(@StartPage int defaultPage) {
        putInt(R.string.default_page_pref, defaultPage);
    }

    @Override
    public void setPrimaryColor(@PresetTheme int colorChoice) {
        putInt(R.string.pref_key_color_primary, colorChoice);
    }

    @Override
    public void setBaseColor(@BaseTheme int themeChoice) {
        putInt(R.string.pref_key_color_base, themeChoice);
    }

    @Override
    public void setShuffle(boolean shuffle) {
        putBoolean(R.string.pref_key_shuffle, shuffle);
    }

    @Override
    public void setRepeatMode(int repeatMode) {
        putInt(R.string.pref_key_repeat, repeatMode);
    }

    @Override
    public void setEqualizerPresetId(int equalizerPresetId) {
        putInt(R.string.pref_key_eq_id, equalizerPresetId);
    }

    @Override
    public void setEqualizerEnabled(boolean equalizerEnabled) {
        putBoolean(R.string.pref_key_eq_enabled, equalizerEnabled);
    }

    @Override
    public void setEqualizerSettings(Equalizer.Settings settings) {
        putString(R.string.pref_key_eq_settings, settings.toString());
    }
}
