import styled from 'styled-components/native';

export const MainView = styled.View`
  margin-left: 16px;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`;

export const TransactionsFlatList = styled.FlatList`
  margin-bottom: 450px;
`;

export const HeaderText = styled.Text`
  font-weight: bold;
  font-size: 20px;
  margin: 20px 0 30px 0;
`;

export const DetailedView = styled.View`
  margin: 0 0 20px 0px;
  padding: 10px 0 10px 46px;
  background-color: ${({pix}) => (pix ? '#f8f8f8' : '#fff')};
`;

export const TransferType = styled.View`
  justify-content: space-between;
  flex: 1;
  flex-direction: row;
  margin-right: 16px;
`;

export const TransferText = styled.Text`
  font-size: 16px;
  margin-bottom: 10px;
`;

export const IdAndDate = styled.View`
  justify-content: space-between;
  flex: 1;
  flex-direction: row;
  margin-right: 20px;
`;

export const IdText = styled.Text`
  color: #828282;
  font-size: 16px;
  margin-bottom: 10px;
`;

export const DateText = styled.Text`
  color: #828282;
  font-size: 16px;
  margin-bottom: 10px;
`;

export const PixView = styled.View`
  padding: 0px 18px;
  height: 22px;
  justify-content: center;
  background-color: #00c1af;
`;

export const PixText = styled.Text`
  font-size: 12px;
  color: #fff;
`;

export const ValueInReais = styled.Text`
  font-size: 18px;
  font-weight: bold;
`;

export const GreenDot = styled.Text`
  height: 14px;
  width: 14px;
  border-radius: 50px;
  border: 2px solid #fff;
  background-color: #00c1af;
  position: absolute;
  top: 42px;
  right: 359px;
  z-index: 2;
`;

export const Grayline = styled.View`
  background-color: #828282;
  height: 130px;
  width: 1px;
  right: 366px;
  position: absolute;
  flex: 1;
  z-index: 1;
`;

export const LoadingView = styled.View`
  background-color: #00c1af;
  justify-content: center;
  align-items: center;
  margin: 150px 100px;
  height: 50px;
  border-radius: 4px;
`;

export const LoadingText = styled.Text`
  font-size: 20px;
  font-weight: bold;
  color: #fff;
`;
