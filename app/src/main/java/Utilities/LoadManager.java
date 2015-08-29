package Utilities;

public class LoadManager {

	/*public static Stack<UrlAndImageviewPlaceholder> urlStack = new Stack<UrlAndImageviewPlaceholder>();
	public static Map<String, Bitmap> map = new HashMap<String, Bitmap>();

	public static void load(String url, ImageView imageview, Bitmap loading) {

		if (url == null)
			return;

		if (map.containsKey(url)) {
			// TODO load from cache
			imageview.setImageBitmap(map.get(url));

		} else {
			// TODO load from Internet
			if (urlStack.isEmpty()) {

				urlStack.push(new UrlAndImageviewPlaceholder(url, imageview));
				loadNext();
			} else {
				urlStack.push(new UrlAndImageviewPlaceholder(url, imageview));
			}

		}
	}

	public static void loadFinished(String url, Bitmap bmp) {
		map.put(url, bmp);
		loadNext();
	}

	public static void loadNext() {
		if (!urlStack.isEmpty()) {
			UrlAndImageviewPlaceholder tmp = urlStack.pop();

			if (map.containsKey(tmp.url)) {
				// TODO load from cache
				tmp.imageview.setImageBitmap(map.get(tmp.url));

			} else {

				new AsynLoadImage(tmp.imageview, tmp.url).execute("");
			}
		}
	}
*/
}
