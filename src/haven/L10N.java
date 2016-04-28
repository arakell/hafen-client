package haven;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class L10N {
    private static final Map<Bundle, Map<String, String>> bundles = new HashMap<>();
    private static final Locale current = CFG.LANGUAGE.get();
    public static final List<Locale> locales = new ArrayList<>();

    public enum Bundle {
	TOOLTIP("tooltip"),
	PAGINA("pagina"),
	WINDOW("window"),
	BUTTON("button"),
	FLOWER("flower"),
	MSG("msg"),
	LABEL("label"),
	ACTION("action");

	private final String name;

	Bundle(String name) {
	    this.name = name;
	}

	public String value() {return name;}

	@Override
	public String toString() {
	    return name;
	}
    }

    static {
	locales.add(CFG.DEFAULT_LANGUAGE);
	locales.add(new Locale("ru"));

	String language = current.getLanguage();
	for (Bundle bundle : Bundle.values()) {
	    bundles.put(bundle, load(bundle.value(), language));
	}
    }

    public static boolean needL10N() {
	return !current.equals(CFG.DEFAULT_LANGUAGE);
    }

    public static String getString(Bundle bundle, String key) {
	return getString(bundle, key, key);
    }

    public static String getString(Bundle bundle, String key, String def) {
	Map<String, String> l10nMap = bundles.get(bundle);
	if(needL10N() && l10nMap != null) {
	    String ll = l10nMap.get(key);
	    if(ll != null) {
		return ll;
	    }
	}
	return def;
    }

    private static Map<String, String> load(String bundle, String langcode) {
	Properties props = new Properties();
	InputStream is = Config.getFileStream("l10n/" + bundle + "_" + langcode + ".properties");
	if(is == null)
	    return null;

	InputStreamReader isr = null;
	try {
	    isr = new InputStreamReader(is, "UTF-8");
	    props.load(isr);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if(isr != null) {
		try {
		    isr.close();
		} catch (IOException e) { // ignored
		}
	    }
	}
	return props.size() > 0 ? new HashMap<>((Map) props) : null;
    }
}
