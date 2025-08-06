import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/user.dart';

class UsersScreen extends StatefulWidget {
  const UsersScreen({super.key});

  @override
  State<UsersScreen> createState() => _UsersScreenState();
}

class _UsersScreenState extends State<UsersScreen> {
  final ApiService _apiService = ApiService();
  List<User> users = [];
  bool isLoading = true;
  String? error;

  @override
  void initState() {
    super.initState();
    _loadUsers();
  }

  Future<void> _loadUsers() async {
    try {
      setState(() {
        isLoading = true;
        error = null;
      });
      final loadedUsers = await _apiService.getUsers();
      setState(() {
        users = loadedUsers;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        error = e.toString();
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Usuarios'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : error != null
              ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text('Error: $error'),
                      ElevatedButton(
                        onPressed: _loadUsers,
                        child: const Text('Reintentar'),
                      ),
                    ],
                  ),
                )
              : users.isEmpty
                  ? const Center(child: Text('No hay usuarios'))
                  : ListView.builder(
                      itemCount: users.length,
                      itemBuilder: (context, index) {
                        final user = users[index];
                        return Card(
                          margin: const EdgeInsets.all(8),
                          child: ListTile(
                            leading: CircleAvatar(
                              child: Text(user.firstName[0]),
                            ),
                            title: Text(user.fullName),
                            subtitle: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(user.email),
                                if (user.city != null) Text('Ciudad: ${user.city}'),
                                if (user.personalGoal != null) Text('Meta: ${user.personalGoal}'),
                              ],
                            ),
                          ),
                        );
                      },
                    ),
      floatingActionButton: FloatingActionButton(
        onPressed: _loadUsers,
        child: const Icon(Icons.refresh),
      ),
    );
  }
}