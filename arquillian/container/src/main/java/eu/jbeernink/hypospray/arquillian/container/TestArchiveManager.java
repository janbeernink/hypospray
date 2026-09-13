package eu.jbeernink.hypospray.arquillian.container;

import org.jboss.shrinkwrap.api.Archive;

class TestArchiveManager {

	private static final TestArchiveManager INSTANCE = new TestArchiveManager();

	private Archive<?> testArchive;

	void setTestArchive(Archive<?> testArchive) {
		this.testArchive = testArchive;
	}

	public Archive<?> getTestArchive() {
		if (testArchive == null) {
			throw new IllegalStateException("No test archive currently set. ");
		}

		return testArchive;
	}

	void clearTestArchive() {
		this.testArchive = null;
	}

	static TestArchiveManager getInstance() {
		return INSTANCE;
	}
}
