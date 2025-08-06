class User {
  final int id;
  final String firstName;
  final String lastName;
  final String email;
  final String? gender;
  final String? city;
  final double? height;
  final double? weight;
  final String? personalGoal;

  User({
    required this.id,
    required this.firstName,
    required this.lastName,
    required this.email,
    this.gender,
    this.city,
    this.height,
    this.weight,
    this.personalGoal,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      firstName: json['first_name'],
      lastName: json['last_name'],
      email: json['email'],
      gender: json['gender'],
      city: json['city'],
      height: json['height']?.toDouble(),
      weight: json['weight']?.toDouble(),
      personalGoal: json['personal_goal'],
    );
  }

  String get fullName => '$firstName $lastName';
}