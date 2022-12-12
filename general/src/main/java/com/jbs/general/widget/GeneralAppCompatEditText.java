package com.jbs.general.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatEditText;

import com.jbs.general.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * <b>implemented filter types: </b>
 * <p/>
 * <b>EMAIL : </b>
 * <ul>
 * <li> must be in lower case</li>
 * <li> must not have character other than defined in allowedEmailChars</li>
 * </ul>
 * <b>PHONE</b>
 * <ul>
 * <li> must not start with zero</li>
 * <li> must allow only 0 to 9 and plus sign</li>
 * <li> must not allow + sign after first char</li>
 * </ul>
 * <b>FULL NAME</b>
 * <ul>
 * <li> must be alphabetic. </li>
 * <li>may include space</li>
 * </ul>
 * <b>first or last name</b>
 * <ul>
 * <li> must be alphabetic</li>
 * <li> must not include space</li>
 * </ul>
 * <b>user name</b>
 * <ul>
 * <li> can be either alphabets or numbers (alphanumeric)</li>
 * <li> may include space</li>
 * </ul>
 * <b>ADDRESS/STATE/zip code</b>
 * <ul>
 * <li>must not have character other than defined in allowedAddressNameChars</li>
 * </ul>
 * <b>PASSWORD</b>
 * <ul>
 * <li>no filters</li>
 * </ul>
 * <b>company name</b>
 * <ul>
 * <li>must not have character other than defined in allowedCompanyNameChars</li>
 * </ul>
 * <b>account name</b>
 * <ul>
 * <li>must not have character other than defined in allowedCompanyNameChars</li>
 * <li>must not start with 0-9,dot,slash,comma or dash symbols</li>
 * </ul>
 * <b>bank name</b>
 * <ul>
 * <li>must not have character other than defined in allowedCompanyNameChars</li>
 * <li>must not start with 0-9,dot,slash,comma or dash symbols</li>
 * </ul>
 * <b>ADDRESS/STATE/zip code</b>
 * <ul>
 * <li>must not have character other than defined in NO_SPECIAL_CHARS</li>
 * </ul>
 * <b>only alphabetic, only numbers, only alphanumeric</b>
 * <ul>
 * <li>1:[a-z][A-Z][space],  2:[0-9],  3:[a-z][A-Z][0-9][space]</li>
 * </ul>
 * To view doc - use CTRL+Q or F2
 */
public class GeneralAppCompatEditText extends AppCompatEditText {

    public static final int EMAIL = 1;
    public static final int PHONE = 2;
    public static final int FULL_NAME = 3;
    public static final int FIRST_LAST_NAME = 4;
    public static final int USER_NAME = 5;
    public static final int ADDRESS = 6;
    public static final int STATE = 7;
    public static final int ZIP_CODE = 8;
    public static final int COMPANY_NAME = 9;
    public static final int PASSWORD = 10;
    public static final int ALPHA_NUMERIC = 11;
    public static final int ONLY_ALPHABETS = 12;
    public static final int ONLY_NUMBERS = 13;
    public static final int NO_SPECIAL_CHARS = 14;
    public static final int ACCOUNT_NAME = 15;
    public static final int BANK_NAME = 16;
    //Multi Language
    public static final int FULL_NAME_MULTI_LANGUAGE = 17;
    public static final int FIRST_LAST_NAME_MULTI_LANGUAGE = 18;
    public static final int ADDRESS_MULTI_LANGUAGE = 19;
    public static final int STATE_MULTI_LANGUAGE = 20;
    public static final int ACCOUNT_NAME_MULTI_LANGUAGE = 21;
    public static final int BANK_NAME_MULTI_LANGUGE = 22;

    private List<InputFilter> inputFilters;
    private TextWatcher filterTextWatcher, occurrenceTextWatcher;

    public GeneralAppCompatEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init(attrs);
    }

    public GeneralAppCompatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(attrs);
    }

    public GeneralAppCompatEditText(Context context) {
        super(context);
        if (!isInEditMode())
            init(null);
    }

    private void init(AttributeSet attrs) {
        setIncludeFontPadding(false);

        inputFilters = new ArrayList<>();

        if (attrs != null) {
            //for not allowing first charactor as space by default
            TypedArray arrEditView = getContext().obtainStyledAttributes(attrs, R.styleable.GeneralEditText);

            boolean allowFirstSpace = arrEditView.getBoolean(R.styleable.GeneralEditText_allowFirstSpace, false);
            if (!allowFirstSpace) {
                filterFirstChars(' ');
            }

            int filterType = arrEditView.getInteger(R.styleable.GeneralEditText_filterType, 0);
            filterInput(filterType);
            arrEditView.recycle();
        }

        //get previous input filters
        InputFilter[] editFilters = getFilters();
        //add them to array list
        Collections.addAll(inputFilters, editFilters);
        //set [previous + new] input filters
        InputFilter[] arrInputFilters = new InputFilter[inputFilters.size()];
        setFilters(inputFilters.toArray(arrInputFilters));

        changeCourserVisibilityAccordingFocus();
    }


    /**
     * adds appropriate input filter according to given type
     *
     * @param filterType filter type
     */
    private void filterInput(int filterType) {
        switch (filterType) {

            case EMAIL:
                inputFilters.add((source, start, end, dest, dStart, dEnd) -> source.toString().toLowerCase());
                addStringFilter(R.string.digits_allowed_email_characters);
                doNotAllowEmoji();
                break;

            case PHONE:
                filterFirstChars('0');
                addStringFilter(R.string.digits_allowed_phone_characters);
                filterCharactorOccurrence('+', 1);
                doNotAllowEmoji();
                break;

            case FULL_NAME:
                addStringFilter(R.string.digits_allowed_person_name_characters);
                doNotAllowEmoji();
                break;

            case FIRST_LAST_NAME:
                doNotAllowAnySpace();
                addStringFilter(R.string.digits_allowed_first_last_name_characters);
                doNotAllowEmoji();
                break;

            case USER_NAME:
            case ALPHA_NUMERIC:
                addStringFilter(R.string.digits_allowed_alphanumeric_name_characters);
                doNotAllowEmoji();
                break;

            case ADDRESS:
            case ZIP_CODE:
            case STATE:
                addStringFilter(R.string.digits_allowed_address_name_characters);
                doNotAllowEmoji();
                break;

            case COMPANY_NAME:
                addStringFilter(R.string.digits_allowed_company_name_characters);
                doNotAllowEmoji();
                break;

            case PASSWORD:
                //no filters
                doNotAllowAnySpace();
                doNotAllowEmoji();
                break;

            case ONLY_ALPHABETS:
                addStringFilter(R.string.digits_allow_alphabets);
                doNotAllowEmoji();
                break;

            case ONLY_NUMBERS:
                addStringFilter(R.string.digits_allow_numeric);
                doNotAllowEmoji();
                break;

            case NO_SPECIAL_CHARS:
                addStringFilter(R.string.digits_no_special_characters);
                doNotAllowEmoji();
                break;

            case ACCOUNT_NAME:
            case BANK_NAME:
                addStringFilter(R.string.digits_allowed_company_name_characters);
                filterFirstChars('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '/', '-', ',');
                doNotAllowEmoji();
                break;

            case FULL_NAME_MULTI_LANGUAGE:
                filterFirstChars(' ');
                doNotAllowStringFilter(R.string.digits_do_not_allow_full_name_multi_language);
                doNotAllowEmoji();
                break;

            case FIRST_LAST_NAME_MULTI_LANGUAGE:
                doNotAllowAnySpace();
                doNotAllowStringFilter(R.string.digits_do_not_allow_first_last_name_multi_language);
                doNotAllowEmoji();
                break;

            case ADDRESS_MULTI_LANGUAGE:
            case STATE_MULTI_LANGUAGE:
                doNotAllowStringFilter(R.string.digits_do_not_allow_address_multi_language);
                doNotAllowEmoji();
                break;

            case ACCOUNT_NAME_MULTI_LANGUAGE:
            case BANK_NAME_MULTI_LANGUGE:
                doNotAllowStringFilter(R.string.digits_do_not_allow_account_name_multi_language);
                filterFirstChars('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '/', '-', ',');
                doNotAllowEmoji();
                break;

            default:
                break;
        }
    }

    /**
     * adds input filter that allows ONLY those characters defined in string resource
     *
     * @param allowedCharsRes string resource for allowed characters
     */
    private void addStringFilter(@StringRes int allowedCharsRes) {
        final String allowedChars = getContext().getString(allowedCharsRes);
        InputFilter allowedCharsFilter = (source, start, end, dest, dStart, dEnd) -> {
            if (source == null) return null;
            //checking allowedCharsRes in input and replacing others with empty string
            boolean containsNotAllowedChars = false;
            for (int i = 0; i < source.length(); i++) {
                if (!allowedChars.contains(String.valueOf(source.charAt(i)))) {
                    containsNotAllowedChars = true;
                    break;
                }
            }
            if (containsNotAllowedChars) {
                return "";
           /* // #IMPORTANT : On Some keyboards; double space turns input to a PERIOD(.) sign. that can be TURNED OFF from settings.
            // SEE HERE : http://teckfront.com/activate-double-space-period-google-key-board-android-lollipop-kitkat-devices/
            // For google keyboard : settings-> language & input -> Google Keyboard -> text Correction -> Double-space period
            // Below is an ugly hack to avoid deleting character on double space when input becomes PERIOD(.) sign.
            return source.equals(".") ?
                    null :
                    "";*/
            }
            return null;
        };
        inputFilters.add(allowedCharsFilter);
    }

    /**
     * Add filter that do not allow only those characters define in string resources
     *
     * @param doNotAllowedCharsRes - string resource for not allowed characters
     */
    private void doNotAllowStringFilter(@StringRes int doNotAllowedCharsRes) {
        final String doNotAllowedChars = getContext().getString(doNotAllowedCharsRes);
        InputFilter doNotAllowedCharsFilter = (source, start, end, dest, dStart, dEnd) -> {
            if (source == null) return null;
            //checking allowedCharsRes in input and replacing others with empty string
            boolean containsNotAllowedChars = true;
            for (int i = 0; i < source.length(); i++) {
                if (doNotAllowedChars.contains(String.valueOf(source.charAt(i)))) {
                    containsNotAllowedChars = false;
                    break;
                }
            }
            if (!containsNotAllowedChars) {
                return "";
       /* // #IMPORTANT : On Some keyboards; double space turns input to a PERIOD(.) sign. that can be TURNED OFF from settings.
        // SEE HERE : http://teckfront.com/activate-double-space-period-google-key-board-android-lollipop-kitkat-devices/
        // For google keyboard : settings-> language & input -> Google Keyboard -> text Correction -> Double-space period
        // Below is an ugly hack to avoid deleting character on double space when input becomes PERIOD(.) sign.
        return source.equals(".") ?
                null :
                "";*/
            }
            return null;
        };
        inputFilters.add(doNotAllowedCharsFilter);
    }

    /**
     * disable Emoji in input
     */
    private void doNotAllowEmoji() {
        InputFilter doNotAllowedEmoji = (source, start, end, dest, dStart, dEnd) -> {
            if (source == null) return null;

            for (int i = 0; i < source.length(); i++) {
                int type = Character.getType(source.charAt(i));

                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        };
        inputFilters.add(doNotAllowedEmoji);
    }

    /**
     * disables allowing space in input
     */
    private void doNotAllowAnySpace() {
        InputFilter inputFilter = (source, start, end, dest, dStart, dEnd) -> {
            if (source == null)
                return "";
            // checking if contains space and replacing it with empty string
            boolean hasSpace = false;
            for (int i = 0; i < source.length(); i++) {
                if (Character.isSpaceChar(source.charAt(i))) {
                    hasSpace = true;
                    break;
                }
            }
            if (hasSpace)
                return "";

            return null;
        };
        inputFilters.add(inputFilter);
    }


    /**
     * changes cursor blinking according to focus on view
     */
    private void changeCourserVisibilityAccordingFocus() {
        //if done key pressed - don't blink cursor
        setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setCursorVisible(false);
                return false;
            }
            return false;
        });

        // if touched on input - blink cursor
        setOnTouchListener((view, event) -> {
            setCursorVisible(true);
            return false;
        });

        // if focus on view is changed - change cursor blinking accordingly
        setOnFocusChangeListener((view, hasFocus) -> setCursorVisible(hasFocus));
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        // if back key press(keyboard down by user) - don't blink cursor
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setCursorVisible(false);
        }
        return super.onKeyPreIme(keyCode, event);
    }

    /**
     * limits charactor occurrence in given input
     *
     * @param charToLimit        charactor to limit for occurrence
     * @param maxFrequencyOfChar maximum frequency of charactor
     */
    private void filterCharactorOccurrence(final Character charToLimit, final int maxFrequencyOfChar) {
        occurrenceTextWatcher = getOccurrenceTextWatcher(charToLimit, maxFrequencyOfChar);
        addTextChangedListener(occurrenceTextWatcher);
    }

    @NonNull
    private TextWatcher getOccurrenceTextWatcher(final Character charToLimit, final int maxFrequencyOfChar) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //count charactor frequency in input....if found and is greater than frequency defined - substring input string from its last occurrence
                if (countMatches(charSequence, String.valueOf(charToLimit)) > maxFrequencyOfChar) {
                    charSequence = charSequence.toString().substring(0, charSequence.toString().lastIndexOf(charToLimit));
                    setText(charSequence);
                    setSelection(getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }


    /**
     * disables given charactor as first charactor in input
     *
     * @param firstChars characters to disable at first
     */
    private void filterFirstChars(final Character... firstChars) {
        final List<Character> firstCharsList = Arrays.asList(firstChars);
        filterTextWatcher = getFilterTextWatcher(firstCharsList);
        addTextChangedListener(filterTextWatcher);
    }

    @NonNull
    private TextWatcher getFilterTextWatcher(final List<Character> firstCharsList) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if first charactor is to be filtered, substring input string from first index to length of string
                if (s.length() > 0 && firstCharsList.contains(s.charAt(0))) {
                    s = s.toString().substring(1, s.length());
                    setText(s);
                    setSelection(getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //clearing text watchers when detaching from window
        if (filterTextWatcher != null) {
            removeTextChangedListener(filterTextWatcher);
            filterTextWatcher = null;
        }
        if (occurrenceTextWatcher != null) {
            removeTextChangedListener(occurrenceTextWatcher);
            occurrenceTextWatcher = null;
        }
        inputFilters.clear();
        setOnFocusChangeListener(null);
        setOnTouchListener(null);
        setOnEditorActionListener(null);
    }

    private int countMatches(CharSequence str, CharSequence sub) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = indexOf(str, sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    private int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
        return cs.toString().indexOf(searchChar.toString(), start);
    }
}