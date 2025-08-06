import 'package:flutter/material.dart';
import 'users_screen.dart';
import 'activities_screen.dart';
import 'chat_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Fitneservice'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text(
              '¡Bienvenido a Fitneservice!',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 20),
            const Text(
              'Tu plataforma de seguimiento de actividades físicas',
              style: TextStyle(fontSize: 16),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 40),
            _buildMenuButton(
              context,
              'Usuarios',
              Icons.people,
              () => Navigator.push(context, MaterialPageRoute(builder: (context) => const UsersScreen())),
            ),
            const SizedBox(height: 16),
            _buildMenuButton(
              context,
              'Actividades',
              Icons.fitness_center,
              () => Navigator.push(context, MaterialPageRoute(builder: (context) => const ActivitiesScreen())),
            ),
            const SizedBox(height: 16),
            _buildMenuButton(
              context,
              'Chat con IA',
              Icons.chat,
              () => Navigator.push(context, MaterialPageRoute(builder: (context) => const ChatScreen())),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildMenuButton(BuildContext context, String title, IconData icon, VoidCallback onPressed) {
    return ElevatedButton.icon(
      onPressed: onPressed,
      icon: Icon(icon),
      label: Text(title),
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.all(16),
        textStyle: const TextStyle(fontSize: 18),
      ),
    );
  }
}