class Activity {
  final int id;
  final int userId;
  final String activityType;
  final String status;
  final DateTime? startTime;
  final DateTime? endTime;
  final double? distance;
  final double? durationMinutes;
  final int? caloriesBurned;
  final String? notes;

  Activity({
    required this.id,
    required this.userId,
    required this.activityType,
    required this.status,
    this.startTime,
    this.endTime,
    this.distance,
    this.durationMinutes,
    this.caloriesBurned,
    this.notes,
  });

  factory Activity.fromJson(Map<String, dynamic> json) {
    return Activity(
      id: json['id'],
      userId: json['user_id'],
      activityType: json['activity_type'],
      status: json['status'],
      startTime: json['start_time'] != null ? DateTime.parse(json['start_time']) : null,
      endTime: json['end_time'] != null ? DateTime.parse(json['end_time']) : null,
      distance: json['distance']?.toDouble(),
      durationMinutes: json['duration_minutes']?.toDouble(),
      caloriesBurned: json['calories_burned'],
      notes: json['notes'],
    );
  }
}