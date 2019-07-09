package com.example.sahiltakkar.moviesapp.data;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MovieDAO_Impl implements MovieDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMovieEntry;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMovieEntry;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMovieEntry;

  public MovieDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovieEntry = new EntityInsertionAdapter<MovieEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `movies`(`priority`,`id`,`title`,`poster`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntry value) {
        stmt.bindLong(1, value.getPriority());
        stmt.bindLong(2, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        final byte[] _tmp;
        _tmp = DbBitmapUtitlity.getBytes(value.getPoster());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindBlob(4, _tmp);
        }
      }
    };
    this.__deletionAdapterOfMovieEntry = new EntityDeletionOrUpdateAdapter<MovieEntry>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `movies` WHERE `priority` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntry value) {
        stmt.bindLong(1, value.getPriority());
      }
    };
    this.__updateAdapterOfMovieEntry = new EntityDeletionOrUpdateAdapter<MovieEntry>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `movies` SET `priority` = ?,`id` = ?,`title` = ?,`poster` = ? WHERE `priority` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntry value) {
        stmt.bindLong(1, value.getPriority());
        stmt.bindLong(2, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        final byte[] _tmp;
        _tmp = DbBitmapUtitlity.getBytes(value.getPoster());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindBlob(4, _tmp);
        }
        stmt.bindLong(5, value.getPriority());
      }
    };
  }

  @Override
  public void insertMovie(MovieEntry movieEntry) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovieEntry.insert(movieEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTask(MovieEntry movieEntry) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfMovieEntry.handle(movieEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateMovie(MovieEntry movieEntry) {
    __db.beginTransaction();
    try {
      __updateAdapterOfMovieEntry.handle(movieEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<MovieEntry>> loadAllMovies() {
    final String _sql = "SELECT * FROM movies ORDER BY priority";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<MovieEntry>>() {
      private Observer _observer;

      @Override
      protected List<MovieEntry> compute() {
        if (_observer == null) {
          _observer = new Observer("movies") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfPoster = _cursor.getColumnIndexOrThrow("poster");
          final List<MovieEntry> _result = new ArrayList<MovieEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MovieEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Bitmap _tmpPoster;
            final byte[] _tmp;
            _tmp = _cursor.getBlob(_cursorIndexOfPoster);
            _tmpPoster = DbBitmapUtitlity.getImage(_tmp);
            _item = new MovieEntry(_tmpTitle,_tmpPoster,_tmpId);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            _item.setPriority(_tmpPriority);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public MovieEntry loadMovieById(int id) {
    final String _sql = "SELECT * FROM movies WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfPoster = _cursor.getColumnIndexOrThrow("poster");
      final MovieEntry _result;
      if(_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final Bitmap _tmpPoster;
        final byte[] _tmp;
        _tmp = _cursor.getBlob(_cursorIndexOfPoster);
        _tmpPoster = DbBitmapUtitlity.getImage(_tmp);
        _result = new MovieEntry(_tmpTitle,_tmpPoster,_tmpId);
        final int _tmpPriority;
        _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
        _result.setPriority(_tmpPriority);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
