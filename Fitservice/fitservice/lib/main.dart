import 'package:flutter/material.dart';
import 'screens/home_screen.dart';

void main() {
  runApp(const FitneserviceApp());
}

class FitneserviceApp extends StatelessWidget {
  const FitneserviceApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Fitneservice',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        useMaterial3: true,
      ),
      home: const HomeScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}