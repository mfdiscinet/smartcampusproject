package com.selim.smartcampus.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Report> __insertionAdapterOfReport;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<Report> __updateAdapterOfReport;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReport = new EntityInsertionAdapter<Report>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reports` (`id`,`title`,`description`,`type`,`status`,`createdDate`,`latitude`,`longitude`,`imageUrl`,`creatorId`,`isEmergency`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Report entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, __ReportType_enumToString(entity.getType()));
        statement.bindString(5, __ReportStatus_enumToString(entity.getStatus()));
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getCreatedDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLongitude());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getImageUrl());
        }
        statement.bindLong(10, entity.getCreatorId());
        final int _tmp_1 = entity.isEmergency() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
      }
    };
    this.__updateAdapterOfReport = new EntityDeletionOrUpdateAdapter<Report>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reports` SET `id` = ?,`title` = ?,`description` = ?,`type` = ?,`status` = ?,`createdDate` = ?,`latitude` = ?,`longitude` = ?,`imageUrl` = ?,`creatorId` = ?,`isEmergency` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Report entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, __ReportType_enumToString(entity.getType()));
        statement.bindString(5, __ReportStatus_enumToString(entity.getStatus()));
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getCreatedDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLongitude());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getImageUrl());
        }
        statement.bindLong(10, entity.getCreatorId());
        final int _tmp_1 = entity.isEmergency() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE reports SET status = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertReport(final Report report, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfReport.insertAndReturnId(report);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReport(final Report report, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReport.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final int reportId, final ReportStatus status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, __ReportStatus_enumToString(status));
        _argIndex = 2;
        _stmt.bindLong(_argIndex, reportId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Report>> getAllReports() {
    final String _sql = "SELECT * FROM reports ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<Report>>() {
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

  @Override
  public Flow<List<Report>> getMyReports(final int userId) {
    final String _sql = "SELECT * FROM reports WHERE creatorId = ? ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<Report>>() {
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

  @Override
  public Flow<List<Report>> getReportsByType(final ReportType type) {
    final String _sql = "SELECT * FROM reports WHERE type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __ReportType_enumToString(type));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<Report>>() {
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

  @Override
  public Flow<List<Report>> searchReports(final String query) {
    final String _sql = "SELECT * FROM reports WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<Report>>() {
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

  private String __ReportType_enumToString(@NonNull final ReportType _value) {
    switch (_value) {
      case HEALTH: return "HEALTH";
      case SECURITY: return "SECURITY";
      case ENVIRONMENT: return "ENVIRONMENT";
      case LOST_FOUND: return "LOST_FOUND";
      case TECHNICAL: return "TECHNICAL";
      case OTHER: return "OTHER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __ReportStatus_enumToString(@NonNull final ReportStatus _value) {
    switch (_value) {
      case OPEN: return "OPEN";
      case IN_PROGRESS: return "IN_PROGRESS";
      case RESOLVED: return "RESOLVED";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
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
