package sdong.common.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.uuid.Generators;

public class Util {
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) +
		// s.substring(19, 23) + s.substring(24);
		return s;
	}

	/**
	 * 
	 * https://www.percona.com/blog/2014/12/19/store-uuid-optimized-way/
	 * SUBSTR(uuid, 15, 4),SUBSTR(uuid, 10, 4),SUBSTR(uuid, 1, 8),SUBSTR(uuid, 20,
	 * 4),SUBSTR(uuid, 25)));
	 * 
	 * @return
	 */
	public static String getUUID_v1() {
		UUID uuid = Generators.timeBasedGenerator().generate();
		String id = uuid.toString();
		logger.debug("original id=" + id);
		id = id.substring(14, 18) + id.substring(9, 13) + id.substring(0, 8) + id.substring(19, 23) + id.substring(24);
		logger.debug("new id=" + id);
		return id;
	}
}
