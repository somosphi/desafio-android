import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import FinancialReport from '../pages/FinancialReport/FinancialReport';
import Receipts from '../pages/Receipts/Receipts';

const App = createStackNavigator();

const AppRoutes: React.FC = () => (
  <App.Navigator
    screenOptions={{
      headerShown: false,
      cardStyle: {
        backgroundColor: '#fff',
      },
    }}>
    <App.Screen name="FinancialReport" component={FinancialReport} />
    <App.Screen name="Receipts" component={Receipts} />
  </App.Navigator>
);

export default AppRoutes;
