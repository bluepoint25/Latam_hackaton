import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';
import '../models/activity.dart';

class ApiService {
  static const String baseUrl = 'https://g423a50680cb55e-fitneservice.adb.us-phoenix-1.oraclecloudapps.com/ords/wks_fitneservice';

  // Obtener usuarios
  Future<List<User>> getUsers() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/users/'));
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        final List<dynamic> items = data['items'] ?? [];
        return items.map((json) => User.fromJson(json)).toList();
      } else {
        throw Exception('Error al obtener usuarios: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // Obtener actividades
  Future<List<Activity>> getActivities() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/activities/'));
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        final List<dynamic> items = data['items'] ?? [];
        return items.map((json) => Activity.fromJson(json)).toList();
      } else {
        throw Exception('Error al obtener actividades: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  // Enviar mensaje a la IA
  Future<String> sendMessageToIA(String message) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/ia/chat'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          "model": "gpt-3.5-turbo",
          "messages": [
            {"role": "user", "content": message}
          ]
        }),
      );
      
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        return data['choices'][0]['message']['content'];
      } else {
        throw Exception('Error al comunicarse con la IA: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión con IA: $e');
    }
  }

  // Crear usuario
  Future<bool> createUser(Map<String, dynamic> userData) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/users/'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(userData),
      );
      return response.statusCode == 201 || response.statusCode == 200;
    } catch (e) {
      throw Exception('Error al crear usuario: $e');
    }
  }

  // Crear actividad
  Future<bool> createActivity(Map<String, dynamic> activityData) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/activities/'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(activityData),
      );
      return response.statusCode == 201 || response.statusCode == 200;
    } catch (e) {
      throw Exception('Error al crear actividad: $e');
    }
  }
}