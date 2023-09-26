package com.manuel.tpfitness.database.daos;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.manuel.tpfitness.database.entities.ExerciseEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExerciseDao_Impl implements ExerciseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ExerciseEntity> __insertionAdapterOfExerciseEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateExercise;

  private final SharedSQLiteStatement __preparedStmtOfDelExercise;

  public ExerciseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExerciseEntity = new EntityInsertionAdapter<ExerciseEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ejercicios` (`id_exercise`,`name_exercise`,`description_exercise`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExerciseEntity value) {
        stmt.bindLong(1, value.getIdExercise());
        if (value.getNameExercise() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameExercise());
        }
        if (value.getDescriptionExercise() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescriptionExercise());
        }
      }
    };
    this.__preparedStmtOfUpdateExercise = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE ejercicios SET name_exercise=?, description_exercise=? WHERE id_exercise=?";
        return _query;
      }
    };
    this.__preparedStmtOfDelExercise = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ejercicios WHERE id_exercise=?";
        return _query;
      }
    };
  }

  @Override
  public Object addExercise(final ExerciseEntity exercise,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfExerciseEntity.insert(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateExercise(final String name, final String description, final int id,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateExercise.acquire();
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, name);
        }
        _argIndex = 2;
        if (description == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, description);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfUpdateExercise.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object delExercise(final int id, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelExercise.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDelExercise.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object getExercises(final Continuation<? super List<ExerciseEntity>> continuation) {
    final String _sql = "SELECT * FROM ejercicios";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ExerciseEntity>>() {
      @Override
      public List<ExerciseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdExercise = CursorUtil.getColumnIndexOrThrow(_cursor, "id_exercise");
          final int _cursorIndexOfNameExercise = CursorUtil.getColumnIndexOrThrow(_cursor, "name_exercise");
          final int _cursorIndexOfDescriptionExercise = CursorUtil.getColumnIndexOrThrow(_cursor, "description_exercise");
          final List<ExerciseEntity> _result = new ArrayList<ExerciseEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ExerciseEntity _item;
            final int _tmpIdExercise;
            _tmpIdExercise = _cursor.getInt(_cursorIndexOfIdExercise);
            final String _tmpNameExercise;
            if (_cursor.isNull(_cursorIndexOfNameExercise)) {
              _tmpNameExercise = null;
            } else {
              _tmpNameExercise = _cursor.getString(_cursorIndexOfNameExercise);
            }
            final String _tmpDescriptionExercise;
            if (_cursor.isNull(_cursorIndexOfDescriptionExercise)) {
              _tmpDescriptionExercise = null;
            } else {
              _tmpDescriptionExercise = _cursor.getString(_cursorIndexOfDescriptionExercise);
            }
            _item = new ExerciseEntity(_tmpIdExercise,_tmpNameExercise,_tmpDescriptionExercise);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
