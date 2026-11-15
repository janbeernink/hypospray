package eu.jbeernink.hypospray.arquillian.container;

import java.util.Optional;

import org.jboss.shrinkwrap.api.Archive;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
class TestArchiveManager {

	private static final TestArchiveManager INSTANCE = new TestArchiveManager();

	@Nullable
	private Archive<?> testArchive;

	void setTestArchive(Archive<?> testArchive) {
		this.testArchive = testArchive;
	}

	public Optional<Archive<?>> getTestArchive() {
		return Optional.ofNullable(testArchive);
	}

	void clearTestArchive() {
		this.testArchive = null;
	}

	static TestArchiveManager getInstance() {
		return INSTANCE;
	}
}
