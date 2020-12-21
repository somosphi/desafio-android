import React, {useState, useEffect, useCallback} from 'react';
import {TouchableOpacity, Text, View} from 'react-native';
import api from '../../services/Api';
import {
  MainView,
  TransactionsFlatList,
  HeaderText,
  TransferType,
  TransferText,
  DetailedView,
  IdAndDate,
  IdText,
  DateText,
  PixView,
  PixText,
  ValueInReais,
  GreenDot,
  Grayline,
  LoadingView,
  LoadingText,
} from './styles';
import {useNavigation} from '@react-navigation/native';
import {formatDate, formatValue} from '../../helpers/helpers';

interface ObjectDTO {
  amount: number;
  createdAt: string;
  description: string;
  id: string;
  tType: string;
  to?: string;
  from?: string;
}

const Transactions: React.FC = () => {
  const {navigate} = useNavigation();
  const [transactions, setTransactions] = useState<Array<ObjectDTO>>();

  async function getTransactions(): Promise<void> {
    try {
      const response = await api.get('/myStatement/20/0');
      setTransactions(response.data.items);
    } catch {
      setTransactions([]);
    }
  }

  useEffect(() => {
    getTransactions();
  }, []);

  const isPix = (tType: string) => {
    const pix = tType !== 'PIXCASHIN' && tType !== 'PIXCASHOUT' ? false : true;

    return pix;
  };

  const navigateToReceipt = useCallback(
    (receiptId: string) => {
      navigate('Receipts', {receiptId});
    },
    [navigate],
  );

  return (
    <View>
      <MainView>
        <HeaderText>Suas Movimentações</HeaderText>
      </MainView>
      {transactions ? (
        <TransactionsFlatList
          data={transactions}
          keyExtractor={(transaction) => transaction.id}
          renderItem={({item: el}) => (
            <TouchableOpacity onPress={() => navigateToReceipt(el.id)}>
              <View>
                <DetailedView pix={isPix(el.tType)}>
                  <TransferType>
                    <TransferText>{el.description}</TransferText>
                    {el.tType === 'PIXCASHIN' || el.tType === 'PIXCASHOUT' ? (
                      <PixView>
                        <PixText>Pix</PixText>
                      </PixView>
                    ) : (
                      <Text />
                    )}
                  </TransferType>
                  <IdAndDate>
                    <IdText>{el.to || el.from}</IdText>
                    <DateText>{formatDate(el.createdAt, true)}</DateText>
                  </IdAndDate>
                  <ValueInReais>
                    {el.tType === 'PIXCASHOUT' || el.tType === 'TRANSFEROUT' ? (
                      <Text>- </Text>
                    ) : (
                      <Text />
                    )}
                    R$ {formatValue(el.amount)}
                  </ValueInReais>
                </DetailedView>
              </View>
              <GreenDot />
              <Grayline />
            </TouchableOpacity>
          )}
        />
      ) : (
        <LoadingView>
          <LoadingText>Carregando...</LoadingText>
        </LoadingView>
      )}
    </View>
  );
};

export default Transactions;
