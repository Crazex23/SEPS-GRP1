package ch.zhaw.arsphema.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstrakte Controller klasse.
 * @author schtoeffel
 *
 */
abstract class AbstractController
{
	enum IngameKeys {
		UP, DOWN, BACK, CONFIRM, SHOT
	}


	static Map<IngameKeys, Boolean> keys = new HashMap<AbstractController.IngameKeys, Boolean>();
	static {
		keys.put(IngameKeys.UP, false);
		keys.put(IngameKeys.DOWN, false);
		keys.put(IngameKeys.SHOT, false);
	};

}
