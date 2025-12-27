package com.selim.smartcampus.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FollowDao_Impl implements FollowDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Follow> __insertionAdapterOfFollow;

  private final EntityDeletionOrUpdateAdapter<Follow> __deletionAdapterOfFollow;

  private final DateConverter __dateConverter = new DateConverter();

  public FollowDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFollow = new EntityInsertionAdapter<Follow>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `follows` (`userId`,`reportId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Follow entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindLong(2, entity.getReportId());
      }
    };
    this.__deletionAdapterOfFollow = new EntityDeletionOrUpdateAdapter<Follow>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `follows` WHERE `userId` = ? AND `reportId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Follow entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindLong(2, entity.getReportId());
      }
    };
  }

  @Override
  public Object followReport(final Follow follow, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFollow.insert(follow);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object unfollowReport(final Follow follow, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFollow.handle(follow);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object isFollowing(final int userId, final int reportId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT * FROM follows WHERE userId = ? AND reportId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, reportId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Report>> getFollowedReports(final int userId) {
    final String _sql = "SELECT r.* FROM reports r INNER JOIN follows f ON r.id = f.reportId WHERE f.userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports",
        "follows"}, new Callable<List<Report>>() {
      @Override
      @NonNull
      public List<Report> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfCreatorId = CursorUtil.getColumnIndexOrThrow(_cursor, "creatorId");
          final int _cursorIndexOfIsEmergency = CursorUtil.getColumnIndexOrThrow(_cursor, "isEmergency");
          final List<Report> _result = new ArrayList<Report>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Report _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final ReportType _tmpType;
            _tmpType = __ReportType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final ReportStatus _tmpStatus;
            _tmpStatus = __ReportStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final Date _tmpCreatedDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedDate);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedDate = _tmp_1;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final int _tmpCreatorId;
            _tmpCreatorId = _cursor.getInt(_cursorIndexOfCreatorId);
            final boolean _tmpIsEmergency;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEmergency);
            _tmpIsEmergency = _tmp_2 != 0;
            _item = new Report(_tmpId,_tmpTitle,_tmpDescription,_tmpType,_tmpStatus,_tmpCreatedDate,_tmpLatitude,_tmpLongitude,_tmpImageUrl,_tmpCreatorId,_tmpIsEmergency);
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
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private ReportType __ReportType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "HEALTH": return ReportType.HEALTH;
      case "SECURITY": return ReportType.SECURITY;
      case "ENVIRONMENT": return ReportType.ENVIRONMENT;
      case "LOST_FOUND": return ReportType.LOST_FOUND;
      case "TECHNICAL": return ReportType.TECHNICAL;
      case "OTHER": return ReportType.OTHER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private ReportStatus __ReportStatus_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "OPEN": return ReportStatus.OPEN;
      case "IN_PROGRESS": return ReportStatus.IN_PROGRESS;
      case "RESOLVED": return ReportStatus.RESOLVED;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
