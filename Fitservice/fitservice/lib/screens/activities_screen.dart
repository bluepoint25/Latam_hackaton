import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/activity.dart';

class ActivitiesScreen extends StatefulWidget {
  const ActivitiesScreen({super.key});

  @override
  State<ActivitiesScreen> createState() => _ActivitiesScreenState();
}

class _ActivitiesScreenState extends State<ActivitiesScreen> {
  final ApiService _apiService = ApiService();
  List<Activity> activities = [];
  bool isLoading = true;
  String? error;

  @override
  void initState() {
    super.initState();
    _loadActivities();
  }

  Future<void> _loadActivities() async {
    try {
      setState(() {
        isLoading = true;
        error = null;
      });
      final loadedActivities = await _apiService.getActivities();
      setState(() {
        activities = loadedActivities;
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
        title: const Text('Actividades'),
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
                        onPressed: _loadActivities,
                        child: const Text('Reintentar'),
                      ),
                    ],
                  ),
                )
              : activities.isEmpty
                  ? const Center(child: Text('No hay actividades'))
                  : ListView.builder(
                      itemCount: activities.length,
                      itemBuilder: (context, index) {
                        final activity = activities[index];
                        return Card(
                          margin: const EdgeInsets.all(8),
                          child: ListTile(
                            leading: Icon(_getActivityIcon(activity.activityType)),
                            title: Text(activity.activityType),
                            subtitle: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Estado: ${activity.status}'),
                                if (activity.distance != null) 
                                  Text('Distancia: ${activity.distance} km'),
                                if (activity.durationMinutes != null) 
                                  Text('Duración: ${activity.durationMinutes} min'),
                                if (activity.caloriesBurned != null) 
                                  Text('Calorías: ${activity.caloriesBurned}'),
                              ],
                            ),
                          ),
                        );
                      },
                    ),
      floatingActionButton: FloatingActionButton(
        onPressed: _loadActivities,
        child: const Icon(Icons.refresh),
      ),
    );
  }

  IconData _getActivityIcon(String activityType) {
    switch (activityType.toUpperCase()) {
      case 'RUNNING':
        return Icons.directions_run;
      case 'WALKING':
        return Icons.directions_walk;
      case 'CYCLING':
        return Icons.directions_bike;
      case 'SWIMMING':
        return Icons.pool;
      case 'GYM':
        return Icons.fitness_center;
      case 'YOGA':
        return Icons.self_improvement;
      default:
        return Icons.sports;
    }
  }
}