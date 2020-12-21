import React from 'react';

import Balance from '../../components/Balance/Balance';
import Transactions from '../../components/Transactions/Transactions';
import {StyledSafeAreaView, ScreenTitle, TextTitle} from './styles';

const FinancialReport: React.FC = () => {
  return (
    <StyledSafeAreaView>
      <ScreenTitle>
        <TextTitle>Extrato</TextTitle>
      </ScreenTitle>
      <Balance />
      <Transactions />
    </StyledSafeAreaView>
  );
};

export default FinancialReport;
